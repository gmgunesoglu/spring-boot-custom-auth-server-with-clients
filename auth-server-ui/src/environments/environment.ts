// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  apiUrl: 'http://localhost:9000',
  production: false,
  authorize_uri: 'http://localhost:9000/oauth2/authorize?',
  client_id: 'client',
  client_secret: 'secret',
  logout_url: 'http://localhost:9000/logout',
  redirect_uri: 'http://127.0.0.1:4200/authorized',
  scope: 'openid',
  response_type: 'code',
  response_mode: 'form_post',
  code_challenge_method: 'S256',
  code_challenge: '8wee4VTVwcMfpCV3NMAXH9LRb8kh3hyw1lw2jeghRq4',
  code_verifier: '13Ia9Z6im4zLtcyShVykfQzZs2GGQBniyBRoG7v2myZ',
  token_url: 'http://localhost:9000/oauth2/token',
  grant_type: 'authorization_code',
  refreshTokenUrl: 'http://localhost:9000/oauth2/token'
}

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
