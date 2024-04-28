//package com.gokhan.authserver.repository;
//
//import com.gokhan.authserver.config.MyLogger;
//import com.gokhan.authserver.entity.Client;
//import org.antlr.v4.runtime.misc.NotNull;
//import org.springframework.lang.NonNull;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
//
//import java.util.*;
//
//public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {
//
//    private final ClientRepository clientRepository;
//    private final MyLogger logger;
//
//    public RegisteredClientRepositoryImpl(ClientRepository clientRepository, MyLogger logger) {
//        this.clientRepository = clientRepository;
//        this.logger = logger;
//    }
//
//    @Override
//    public void save(RegisteredClient registeredClient) {
//        Client client = registeredClientToClient(registeredClient);
//        client.setId(null);
//        clientRepository.save(client);
//    }
//
//    @Override
//    public RegisteredClient findById(String id) {
//        long longId = tryStringIdParseToLong(id);
//        Client client = clientRepository.findById(longId).orElseThrow(
//                () -> new UsernameNotFoundException("Client not found with id: " + id)
//        );
//        return clientToRegisteredClient(client);
//    }
//
//    @Override
//    public RegisteredClient findByClientId(String clientId) {
//        if (clientRepository.findByClientId(clientId).isEmpty()){
//            return null;
//        }
//        Client client = clientRepository.findByClientId(clientId).orElseThrow(
//                () -> new UsernameNotFoundException("Client not found with clientId: " + clientId)
//        );
//        return clientToRegisteredClient(client);
//    }
//
//    private RegisteredClient clientToRegisteredClient(Client client) {
//        return RegisteredClient
//                .withId(client.getId().toString())
//                .clientId(client.getClientId())
//                .clientSecret(client.getClientSecret())
//                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes
//                        .addAll(getAuthorizationGrantTypes(client)))
//                .redirectUris(uri -> uri.addAll(Arrays.stream(client.getRedirectUris().split(",")).toList()))
//                .scopes(scopes -> scopes
//                        .addAll(Arrays.stream(client.getScopes().split(",")).toList()))
//                .clientAuthenticationMethods(cam -> cam
//                        .addAll(getClientAuthenticationMethods(client)))
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//    }
//
//    private Client registeredClientToClient(RegisteredClient registeredClient) {
//        Client client = new Client();
//        if(registeredClient.getId() != null) {
//            try {
//                client.setId(tryStringIdParseToLong(registeredClient.getId()));
//            } catch (IllegalArgumentException e) {
//                client.setId(null);
//            } catch (Exception e){
//                logger.error("Unexpected error: " + e.toString());
//                throw new IllegalArgumentException("Unexpected error: " + e.toString());
//            }
//        }
//        client.setClientId(registeredClient.getClientId());
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
//        client.setClientName(registeredClient.getClientName());
//        client.setClientAuthenticationMethods(getRegisteredClientAuthenticationMethods(registeredClient));
//        client.setAuthorizationGrantTypes(getRegisteredClientAuthorizationGrantTypes(registeredClient));
//        client.setRedirectUris(concatStrings(registeredClient.getRedirectUris()));
//        client.setPostLogoutRedirectUris(concatStrings(registeredClient.getPostLogoutRedirectUris()));
//        client.setScopes(concatStrings(registeredClient.getScopes()));
//        client.setClientSettings(registeredClient.getClientSettings().toString());
//        client.setTokenSettings(registeredClient.getTokenSettings().toString());
//        return client;
//    }
//
//    private List<AuthorizationGrantType> getAuthorizationGrantTypes(Client client){
//        List<AuthorizationGrantType> authorizationGrantTypes = new ArrayList<>();
//        String[] strArray = client.getAuthorizationGrantTypes().split(",");
//        for(String str : strArray){
//            authorizationGrantTypes.add(new AuthorizationGrantType(str));
//        }
//        return authorizationGrantTypes;
//    }
//
//    private List<ClientAuthenticationMethod>  getClientAuthenticationMethods(Client client) {
//        List<ClientAuthenticationMethod> clientAuthenticationMethods = new ArrayList<>();
//        String[] strArray = client.getClientAuthenticationMethods().split(",");
//        for(String str : strArray){
//            clientAuthenticationMethods.add(new ClientAuthenticationMethod(str));
//        }
//        return clientAuthenticationMethods;
//    }
//
//    private Long tryStringIdParseToLong(String strId){
//        long id;
//        try {
//            id = Long.parseLong(strId);
//        } catch (NumberFormatException e) {
//            logger.error("Client id could not parse to long: {}" + strId);
//            throw new IllegalArgumentException("Client id could not parse to long: " + strId, e);
//        }
//        return id;
//    }
//
//    private String getRegisteredClientAuthenticationMethods(RegisteredClient registeredClient) {
//        StringBuilder clientAuthenticationMethods = new StringBuilder();
//        for(ClientAuthenticationMethod method : registeredClient.getClientAuthenticationMethods()){
//            clientAuthenticationMethods.append(method.getValue()).append(",");
//        }
//        return clientAuthenticationMethods.substring(0, clientAuthenticationMethods.length()-1);
//    }
//
//    private String getRegisteredClientAuthorizationGrantTypes(RegisteredClient registeredClient) {
//        StringBuilder authorizationGrantTypes = new StringBuilder();
//        for(AuthorizationGrantType grandType : registeredClient.getAuthorizationGrantTypes()){
//            authorizationGrantTypes.append(grandType.getValue()).append(",");
//        }
//        return authorizationGrantTypes.substring(0, authorizationGrantTypes.length()-1);
//    }
//
//    private String concatStrings(Set<String> strings) {
//        if (strings.isEmpty()){
//            return "";
//        }
//        StringBuilder concatString = new StringBuilder();
//        for(String str : strings){
//            concatString.append(str).append(",");
//        }
//        return concatString.substring(0, concatString.length()-1);
//    }
//}
