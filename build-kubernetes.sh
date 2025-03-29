#!/bin/bash

build_postgres() {
# setup postgresql
  echo "Setup postgresql"
  kubectl apply -f kubernetes/postgresql-volume.yaml
  kubectl apply -f kubernetes/postgresql-deployment.yaml
  kubectl apply -f kubernetes/postgresql-service.yaml
}

build_adres_app() {
# setup adres-app
  echo "Setup adres-app"
  kubectl apply -f kubernetes/adres-app-deployment.yaml
  kubectl apply -f kubernetes/adres-app-service.yaml
}

build_prometheus() {
# setup prometheus
  echo "Setup prometheus"
  kubectl apply -f kubernetes/prometheus-config.yaml
  kubectl apply -f kubernetes/prometheus-deployment.yaml
  kubectl apply -f kubernetes/prometheus-service.yaml
}

build_grafana() {
# Setup grafana
  echo "Setup grafana"
  kubectl apply -f kubernetes/grafana-volume.yaml
  kubectl apply -f kubernetes/grafana-secret.yaml
  kubectl apply -f kubernetes/grafana-datasources.yaml
  kubectl apply -f kubernetes/grafana-dashboard-provider.yaml
  kubectl apply -f kubernetes/grafana-dashboards.yaml
  kubectl apply -f kubernetes/grafana-deployment.yaml
  kubectl apply -f kubernetes/grafana-service.yaml
}

build_angular_app() {
# Setup the angular-app
  echo "Setup adres-app"
  kubectl apply -f kubernetes/angular-app-deployment.yaml
  kubectl apply -f kubernetes/angular-app-service.yaml
}

build_all() {
  build_postgres
  build_adres_app
  build_prometheus
  build_grafana
  build_angular_app
}

if [ $# -eq 0 ]; then
    echo "No arguments provided. Deleting all resources."
    build_all
else
    for arg in "$@"; do
        case $arg in
            angular-app) build_angular_app ;;
            grafana) build_grafana ;;
            prometheus) build_prometheus ;;
            adres-app) build_adres_app ;;
            postgresql) build_postgresql ;;
            all) build_all ;;
            *)
                echo "Unknown option: $arg"
                echo "Valid options: angular-app, grafana, prometheus, adres-app, postgresql, all"
                ;;
        esac
    done
fi
