package com.gokhan.authserver.service;

import com.gokhan.authserver.config.MyLogger;
import com.gokhan.authserver.dto.client.ClientDetailDto;
import com.gokhan.authserver.dto.client.ClientDto;
import com.gokhan.authserver.dto.client.ClientRegisterDto;
import com.gokhan.authserver.dto.client.ClientUpdateDto;
import com.gokhan.authserver.entity.Client;
import com.gokhan.authserver.entity.ResourceServer;
import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
import com.gokhan.authserver.repository.ClientRepository;
import com.gokhan.authserver.repository.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    private final MyLogger logger;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ClientRepository clientRepository;
    private final RealmRepository realmRepository;
    private final ClientSettings clientSettings;

    @Autowired
    public ClientServiceImpl(MyLogger logger, ClientRepository clientRepository, RealmRepository realmRepository, ClientSettings clientSettings) {
        this.logger = logger;
        this.clientRepository = clientRepository;
        this.realmRepository = realmRepository;
        this.clientSettings = clientSettings;
    }

    // RegisteredClient...

    @Override
    public void save(RegisteredClient registeredClient) {
        Client client = registeredClientToClient(registeredClient);
        client.setId(null);
        clientRepository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        long longId = tryStringIdParseToLong(id);
        Client client = clientRepository.findById(longId).orElseThrow(
                () -> new UsernameNotFoundException("Client not found with id: " + id)
        );
        return clientToRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {   // clientId = demo_client
        Client client = clientRepository.findByName(clientId).orElseThrow(
                () -> new UsernameNotFoundException("Client not found with clientId: " + clientId)
        );
        return clientToRegisteredClient(client);
    }


    // Client...

    @Override
    public ClientDetailDto add(ClientRegisterDto clientRegisterDto) {
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(clientRegisterDto.getName())
                .clientSecret(passwordEncoder.encode(clientRegisterDto.getClientSecret()))
                .clientName(clientRegisterDto.getName())
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes
                        .addAll(clientRegisterDto.getAuthorizationGrantTypes()))
                .redirectUris(uri -> uri
                        .add(clientRegisterDto.getRedirectUri()))
//                .postLogoutRedirectUris(uri -> uri
//                        .add(clientRegisterDto.getPostLogoutRedirectUri()))
//                .scopes(scopes -> scopes
////                        .addAll(clientRegisterDto.getScopes()))
//                        .add("openid")) //scopes disabled
                .scopes(scopes -> scopes.add("openid"))
                .clientAuthenticationMethods(cam -> cam
                        .add(clientRegisterDto.getClientAuthenticationMethod()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .clientIdIssuedAt(Instant.now())
                .build();
        Client client = registeredClientToClient(registeredClient);
        client.setBaseUrl(clientRegisterDto.getBaseUrl());
        client.setRealm(realmRepository.findByName(clientRegisterDto.getRealmName()).orElseThrow(
                () -> new UsernameNotFoundException("Realm not found with name: " + clientRegisterDto.getRealmName())
        ));
        client = clientRepository.save(client);
        return clientToClientDetailDto(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new UnsupportedOperationException("Client not found with id: " + id)
        );
    }

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.listClientDto();
    }

    @Override
    public ClientDetailDto findClientByName(String name) {
        Client client = clientRepository.findByName(name).orElseThrow(
                () -> new UnsupportedOperationException("Client not found with name: " + name)
        );
        return clientToClientDetailDto(client);
    }

    @Override
    public ClientDetailDto update(String name, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findByName(name).orElseThrow(
                () -> new UsernameNotFoundException("Client not found with name: " + name)
        );
        if (clientUpdateDto.getName() != null){
            if(clientUpdateDto.getName().length() < 3 || clientUpdateDto.getName().length() > 30){
                throw new GlobalRuntimeException("Client name must be a minimum of 3 and maximum 30 characters long", HttpStatus.BAD_REQUEST);
            }
            client.setName(clientUpdateDto.getName());
        }
        if (clientUpdateDto.getClientSecret() != null) {
            if(clientUpdateDto.getClientSecret().length() < 64){
                throw new GlobalRuntimeException("Client secret key must be a minimum of 64 characters long", HttpStatus.BAD_REQUEST);
            }
            client.setClientSecret(passwordEncoder.encode(clientUpdateDto.getClientSecret()));
        }
        if (clientUpdateDto.getBaseUrl() != null && !clientUpdateDto.getBaseUrl().isEmpty()){
            client.setBaseUrl(clientUpdateDto.getBaseUrl());
        }
        if (clientUpdateDto.getAuthorizationGrantTypes() != null && !clientUpdateDto.getAuthorizationGrantTypes().isEmpty()){
            client.setAuthorizationGrantTypes(concatStrings(clientUpdateDto.getAuthorizationGrantTypes()));
        }
        if (clientUpdateDto.getRedirectUris() != null && !clientUpdateDto.getRedirectUris().isEmpty()){
            client.setRedirectUris(clientUpdateDto.getRedirectUris());
        }
        if (clientUpdateDto.getPostLogoutRedirectUris() != null && !clientUpdateDto.getPostLogoutRedirectUris().isEmpty()){
            client.setPostLogoutRedirectUris(clientUpdateDto.getPostLogoutRedirectUris());
        }
        if (clientUpdateDto.getClientAuthenticationMethod() != null){
            client.setClientAuthenticationMethods(clientUpdateDto.getClientAuthenticationMethod().getValue());
        }
        client = clientRepository.save(client);
        return clientToClientDetailDto(client);
    }

    @Override
    public ClientDetailDto delete(String clientId) {
        return null;
    }

    private RegisteredClient clientToRegisteredClient(Client client) {
        return RegisteredClient
                .withId(client.getId().toString())
                .clientId(client.getName())
                .clientSecret(client.getClientSecret())
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes
                        .addAll(getAuthorizationGrantTypes(client)))
                .redirectUris(uri -> uri.addAll(Arrays.stream(client.getRedirectUris().split(",")).toList()))
                .scopes(scopes -> scopes
                        .addAll(Arrays.stream(client.getScopes().split(",")).toList()))
                .clientAuthenticationMethods(cam -> cam
                        .addAll(getClientAuthenticationMethods(client)))
                .clientSettings(clientSettings)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
    }

    private Client registeredClientToClient(RegisteredClient registeredClient) {
//        Client client = new Client();
//        client.setName(registeredClient.getClientId());
//        if (registeredClient.getClientIdIssuedAt() == null) {
//            logger.error("ClientIdIssuedAt cannot be null");
//            logger.error("RegisteredClient: " + registeredClient);
//            throw new IllegalArgumentException("ClientIdIssuedAt cannot be null");
//        }
//        client.setClientIdIssuedAt(Date.from(registeredClient.getClientIdIssuedAt()));
//        client.setClientSecret(registeredClient.getClientSecret());
//        if (registeredClient.getClientSecretExpiresAt() == null) {
//            logger.info("ClientSecretExpiresAt is null");
//            logger.info("RegisteredClient: " + registeredClient);
//        }else{
//            client.setClientSecretExpiresAt(Date.from(registeredClient.getClientSecretExpiresAt()));
//        }
//        client.setName(registeredClient.getClientName());
//        client.setClientAuthenticationMethods(getRegisteredClientAuthenticationMethods(registeredClient));
//        client.setAuthorizationGrantTypes(getRegisteredClientAuthorizationGrantTypes(registeredClient));
//        client.setRedirectUris(concatStrings(registeredClient.getRedirectUris()));
//        client.setPostLogoutRedirectUris(concatStrings(registeredClient.getPostLogoutRedirectUris()));
//        client.setScopes(concatStrings(registeredClient.getScopes()));
//        client.setClientSettings(registeredClient.getClientSettings().toString());
//        client.setTokenSettings(registeredClient.getTokenSettings().toString());
        return Client.builder()
                .name(registeredClient.getClientName())
                .clientIdIssuedAt(
                        registeredClient.getClientIdIssuedAt() != null ?
                                Date.from(registeredClient.getClientIdIssuedAt()): null)
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(
                        registeredClient.getClientSecretExpiresAt() != null ?
                                Date.from(registeredClient.getClientSecretExpiresAt()) : null)
                .clientAuthenticationMethods(getRegisteredClientAuthenticationMethods(registeredClient))
                .authorizationGrantTypes(getRegisteredClientAuthorizationGrantTypes(registeredClient))
                .redirectUris(concatStrings(registeredClient.getRedirectUris()))
//                .postLogoutRedirectUris(concatStrings(registeredClient.getPostLogoutRedirectUris()))
                .scopes(concatStrings(registeredClient.getScopes()))
                .clientSettings(registeredClient.getClientSettings().toString())
                .tokenSettings(registeredClient.getTokenSettings().toString())
                .build();
    }

    private List<AuthorizationGrantType> getAuthorizationGrantTypes(Client client){
        List<AuthorizationGrantType> authorizationGrantTypes = new ArrayList<>();
        String[] strArray = client.getAuthorizationGrantTypes().split(",");
        for(String str : strArray){
            authorizationGrantTypes.add(new AuthorizationGrantType(str));
        }
        return authorizationGrantTypes;
    }

    private List<ClientAuthenticationMethod>  getClientAuthenticationMethods(Client client) {
        List<ClientAuthenticationMethod> clientAuthenticationMethods = new ArrayList<>();
        String[] strArray = client.getClientAuthenticationMethods().split(",");
        for(String str : strArray){
            clientAuthenticationMethods.add(new ClientAuthenticationMethod(str));
        }
        return clientAuthenticationMethods;
    }

    private Long tryStringIdParseToLong(String strId){
        long id;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            logger.error("Client id could not parse to long: {}" + strId);
            throw new IllegalArgumentException("Client id could not parse to long: " + strId, e);
        }
        return id;
    }

    // yeniden düzenlenebilir
    private String getRegisteredClientAuthenticationMethods(RegisteredClient registeredClient) {
        StringBuilder clientAuthenticationMethods = new StringBuilder();
        for(ClientAuthenticationMethod method : registeredClient.getClientAuthenticationMethods()){
            clientAuthenticationMethods.append(method.getValue()).append(",");
        }
        return clientAuthenticationMethods.substring(0, clientAuthenticationMethods.length()-1);
    }

    private String getRegisteredClientAuthorizationGrantTypes(RegisteredClient registeredClient) {
        StringBuilder authorizationGrantTypes = new StringBuilder();
        for(AuthorizationGrantType grandType : registeredClient.getAuthorizationGrantTypes()){
            authorizationGrantTypes.append(grandType.getValue()).append(",");
        }
        return authorizationGrantTypes.substring(0, authorizationGrantTypes.length()-1);
    }

    private String concatStrings(Set<String> strings) {
        if (strings.isEmpty()){
            return "";
        }
        StringBuilder concatString = new StringBuilder();
        for(String str : strings){
            concatString.append(str).append(",");
        }
        return concatString.substring(0, concatString.length()-1);
    }
    private String concatStrings(List<AuthorizationGrantType> authorizationGrantTypes) {
        StringBuilder concatString = new StringBuilder();
        for(AuthorizationGrantType authType : authorizationGrantTypes){
            concatString.append(authType.getValue()).append(",");
        }
        return concatString.substring(0, concatString.length()-1);
    }

    private ClientDetailDto clientToClientDetailDto(Client client) {
        List<String> usersName = new ArrayList<>();
        List<String> resourceServersName = new ArrayList<>();
        if(client.getResourceServers() != null){
            for (ResourceServer resourceServer : client.getResourceServers()) {
                resourceServersName.add(resourceServer.getName());
            }
        }
        if(client.getUsers() != null){
            for (User user : client.getUsers()) {
                usersName.add(user.getName());
            }
        }
        return ClientDetailDto.builder()
                .superUserName(client.getRealm().getSuperUser().getName())
                .realmName(client.getRealm().getName())
                .name(client.getName())
                .usersName(usersName)
                .resourceServersName(resourceServersName)
                .build();
    }
}
