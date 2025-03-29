#!/bin/bash

delete_angular_app() {
    echo "Delete angular-app"
    kubectl delete service angular-app
    kubectl delete deployment angular-app
}

delete_grafana() {
    echo "Delete grafana"
    kubectl delete service grafana
    kubectl delete deployment grafana
    kubectl delete configmap grafana-dashboards
    kubectl delete configmap grafana-dashboard-provider
    kubectl delete configmap grafana-datasource
    kubectl delete secret grafana-secret
    kubectl delete persistentvolumeclaim grafana-pvc
}

delete_prometheus() {
    echo "Delete prometheus"
    kubectl delete service prometheus
    kubectl delete deployment prometheus
    kubectl delete configmap prometheus-config
}

delete_adres_app() {
    echo "Delete adres-app"
    kubectl delete service adres-app
    kubectl delete deployment adres-app
}

delete_postgresql() {
    echo "Delete postgresql"
    kubectl delete service postgres
    kubectl delete deployment postgres
    kubectl delete persistentvolumeclaim postgres-pvc
}

# default order
delete_all() {
    delete_angular_app
    delete_grafana
    delete_prometheus
    delete_adres_app
    delete_postgresql
}

if [ $# -eq 0 ]; then
    echo "No arguments provided. Deleting all resources."
    delete_all
else
    for arg in "$@"; do
        case $arg in
            angular-app) delete_angular_app ;;
            grafana) delete_grafana ;;
            prometheus) delete_prometheus ;;
            adres-app) delete_adres_app ;;
            postgresql) delete_postgresql ;;
            all) delete_all ;;
            *)
                echo "Unknown option: $arg"
                echo "Valid options: angular-app, grafana, prometheus, adres-app, postgresql, all"
                ;;
        esac
    done
fi
