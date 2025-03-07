// src/app/env.d.ts
interface Env {
  API_BASE_URL?: string;
}

interface Window {
  env?: Env;
}
