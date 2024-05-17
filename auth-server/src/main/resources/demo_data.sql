DO $$
    DECLARE
        user_id bigint; realm_id bigint; client_id bigint; role_id bigint; resource_server_id bigint; policy_id bigint;
    BEGIN

        -- Demo SuperUser with Role -> SUPER_USER. password -> password
        INSERT INTO users (id, account_locked, enabled, username, password)
            VALUES (nextval('user_seq'), false, true, 'demo_super_user', '$2a$12$eEw4QT5PppX5ff1wcH1TLeRIQ0V91vw54fkjQz7cZ9zQoNcWS3nBy');
        user_id := (SELECT currval('user_seq'));
        INSERT INTO users_roles (id, roles_id, users_id)
            VALUES (nextval('users_roles_seq'), 1, user_id);

        -- Demo Realm
        INSERT INTO realm (id, super_user_id, name)
            VALUES (nextval('realm_seq'), user_id, 'demo_realm');

        -- Demo Clients. client_secret -> client_secret
        realm_id := (SELECT currval('realm_seq'));
        INSERT INTO client (
                            client_id_issued_at,
                            client_secret_expires_at,
                            id,
                            realm_id,
                            name,
                            scopes,
                            client_secret,
                            authorization_grant_types,
                            client_authentication_methods,
                            post_logout_redirect_uris,
                            redirect_uris,
                            client_settings,
                            token_settings,
                            base_url
        ) VALUES (
                  CURRENT_TIMESTAMP,
                  NULL,
                  nextval('client_seq'),
                  realm_id,
                  'demo_client',
                  'openid',
                  '$2a$12$Swaeg39C7BtoCSs4gSYTtucVCVrkTSn0WprnfcbP3nwkgOY.Fb9Ji',
                  'refresh_token,client_credentials,authorization_code',
                  'client_secret_basic',
                  'http://127.0.0.1:8090/logout',
                  'http://127.0.0.1:8090/login/oauth2/code/reg-client',
                  'AbstractSettings {settings={settings.client.require-proof-key=false, settings.client.require-authorization-consent=true}}',
                  'AbstractSettings {settings={settings.token.reuse-refresh-tokens=true, settings.token.id-token-signature-algorithm=RS256, settings.token.access-token-time-to-live=PT5M, settings.token.access-token-format=org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat@face4f32, settings.token.refresh-token-time-to-live=PT1H, settings.token.authorization-code-time-to-live=PT5M, settings.token.device-code-time-to-live=PT5M}}',
                  'http://127.0.0.1:8090'
                  );

        -- Demo Roles
        INSERT INTO role (id, realm_id, name)
            VALUES (nextval('role_seq'), realm_id, 'MEMBER'),
                   (nextval('role_seq'), realm_id, 'CUSTOMER'),
                   (nextval('role_seq'), realm_id, 'STAFF');

        -- Demo Users, passwords -> password
        client_id := (SELECT currval('client_seq'));
        INSERT INTO users (account_locked, enabled, client_id, id, username, password)
            VALUES (FALSE, TRUE, client_id, nextval('user_seq'), 'member', '$2a$12$eEw4QT5PppX5ff1wcH1TLeRIQ0V91vw54fkjQz7cZ9zQoNcWS3nBy'),
                   (FALSE, TRUE, client_id, nextval('user_seq'), 'customer', '$2a$12$eEw4QT5PppX5ff1wcH1TLeRIQ0V91vw54fkjQz7cZ9zQoNcWS3nBy'),
                   (FALSE, TRUE, client_id, nextval('user_seq'), 'staff', '$2a$12$eEw4QT5PppX5ff1wcH1TLeRIQ0V91vw54fkjQz7cZ9zQoNcWS3nBy');
        role_id := (SELECT currval('role_seq'));
        user_id := (SELECT currval('user_seq'));
        INSERT INTO users_roles (id, roles_id, users_id)
            VALUES (nextval('users_roles_seq'), role_id-2, user_id-2),
                   (nextval('users_roles_seq'), role_id-1, user_id-1),
                   (nextval('users_roles_seq'), role_id, user_id);

        -- Demo ResourceServer
        INSERT INTO resource_server (client_id, id, name, base_url)
            VALUES (client_id, nextval('resource_server_seq'), 'demo_resource_server', 'http://localhost:8080');

        -- Policies
        resource_server_id := (SELECT currval('resource_server_seq'));
        INSERT INTO policy (method_type, id, resource_server_id, uri)
            VALUES (0, nextval('policy_seq'), resource_server_id, '/test/member'),
                   (0, nextval('policy_seq'), resource_server_id, '/test/customer'),
                   (0, nextval('policy_seq'), resource_server_id, '/test/staff');
        policy_id := (SELECT currval('policy_seq'));
        INSERT INTO policy_roles (id, policies_id, roles_id)
            VALUES (nextval('policy_roles_seq'), policy_id-2, role_id-2),
                   (nextval('policy_roles_seq'), policy_id-2, role_id-1),
                   (nextval('policy_roles_seq'), policy_id-2, role_id),
                   (nextval('policy_roles_seq'), policy_id-1, role_id-1),
                   (nextval('policy_roles_seq'), policy_id-1, role_id),
                   (nextval('policy_roles_seq'), policy_id, role_id);
    END $$;
