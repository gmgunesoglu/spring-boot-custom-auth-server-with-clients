package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.user.*;
import com.gokhan.authserver.entity.*;
import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
import com.gokhan.authserver.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRolesRepository userRolesRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetailDto addSuperUser(SuperUserRegisterDto superUserRegisterDto) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("SUPER_USER").orElse(null);
        roles.add(role);
        User user = User.builder()
                .username(superUserRegisterDto.getUsername())
                .password(passwordEncoder.encode(superUserRegisterDto.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .build();
        user = userRepository.save(user);
        userRolesRepository.save(UserRoles.builder().user(user).role(role).build());
        user.setRoles(roles);
        return userToUserDetailDto(user);
    }

    @Override
    public UserDetailDto add(UserRegisterDto userRegisterDto) {
        Client client = clientRepository.findByName(userRegisterDto.getClientName()).orElseThrow(
                () -> new UsernameNotFoundException("Client not found with name: " + userRegisterDto.getClientName())
        );
        List<Role> roles = new ArrayList<>();
        for(String strRole : userRegisterDto.getRoles()) {
            Role role = roleRepository.findByNameAndRealm(strRole, client.getRealm()).orElseThrow(
                    () -> new UsernameNotFoundException("Role " + strRole + " not found")
            );
            roles.add(role);
        }

        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .client(client)
                .build();
        userRepository.save(user);
        for (Role role : roles) {
            userRolesRepository.save(UserRoles.builder().user(user).role(role).build());
        }
        user.setRoles(roles);
        return userToUserDetailDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users) {
            List<String> roleNames = new ArrayList<>();
            for(Role role : user.getRoles()) {
                roleNames.add(role.getName());
            }
            userDtoList.add(UserDto.builder()
                        .username(user.getUsername())
                        .clientName(user.getClient() != null ? user.getClient().getName() : null)
                        .realmName(user.getRealm() != null ? user.getRealm().getName() : null)
                        .build());
        }
        return userDtoList;
    }

    @Override
    public List<UserDto> getAllByUsername(String username) {
        List<User> users = userRepository.findAllByUsernameStartingWith(username);
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users) {
            List<String> roleNames = new ArrayList<>();
            for(Role role : user.getRoles()) {
                roleNames.add(role.getName());
            }
            userDtoList.add(UserDto.builder()
                    .username(user.getUsername())
                    .clientName(user.getClient() != null ? user.getClient().getName() : null)
                    .realmName(user.getRealm() != null ? user.getRealm().getName() : null)
                    .build());
        }
        return userDtoList;
    }

    @Override
    public UserDetailDto get(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );
        return userToUserDetailDto(user);
    }

    @Override
    public UserDetailDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        return userToUserDetailDto(user);
    }



    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    @Transactional
    public UserDetailDto setRoles(String username, UserSetRoleDto userSetRoleDto) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );

        // don't add news if also in olds & chose will be deleted
        List<String> theRoles = userSetRoleDto.getRoles();
        List<UserRoles> willBeDelete = new ArrayList<>();
        List<UserRoles> willBeAdd = new ArrayList<>();
        for (Role role : user.getRoles()){
            if (theRoles.contains(role.getName())){
                theRoles.remove(role.getName());
            }else{
                UserRoles userRoles = userRolesRepository.findByUserIdAndRoleId(user.getId(), role.getId()).orElseThrow(
                        () -> new GlobalRuntimeException("UserRoles not found!", HttpStatus.INTERNAL_SERVER_ERROR)
                );
                willBeDelete.add(userRoles);
            }
        }

        // save news
        for (String roleName : theRoles){
            Role role = roleRepository.findByName(roleName).orElseThrow(
                    () -> new GlobalRuntimeException("Role not found with name: " + roleName, HttpStatus.BAD_REQUEST)
            );
            UserRoles userRoles = UserRoles.builder().user(user).role(role).build();
            willBeAdd.add(userRoles);
        }

        // remove olds
        userRolesRepository.deleteAll(willBeDelete);
        userRolesRepository.flush();
        userRolesRepository.saveAllAndFlush(willBeAdd);
        userRolesRepository.flush();
        User userWithNewRoles = userRepository.findById(user.getId()).get();
        return userToUserDetailDto(userWithNewRoles);
    }

    @Override
    public UserDetailDto delete(Long id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with name: " + username)
        );
    }

    @Override
    public UserDetailDto block(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with name: " + username)
        );
        user.setAccountLocked(true);
        user = userRepository.save(user);
        return userToUserDetailDto(user);
    }

    @Override
    public UserDetailDto unblock(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with name: " + username)
        );
        user.setAccountLocked(false);
        user = userRepository.save(user);
        return userToUserDetailDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }

    private UserDetailDto userToUserDetailDto(User user) {
        return UserDetailDto.builder()
                .username(user.getName())
                .roles(getRolesName(user))
                .realmName(user.getRealm() != null ?
                        user.getRealm().getName() : null)
                .clientName(user.getClient() != null ?
                        user.getClient().getName() : null)
                .blocked(user.isAccountLocked())
                .build();
    }

    private List<String> getRolesName(User user) {
        List<String> roles = new ArrayList<>();
        for(Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        return roles;
    }

    private Long getRequesterUser(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(authentication);
        return 0L;
    }
}
