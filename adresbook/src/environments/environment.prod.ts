export const environment = {
    production: true,
    enableServiceWorker: true,
    apiUrl: window["env"] && window["env"]["API_BASE_URL"] || "http://localhost:8080/adres/api/v1"
  };
