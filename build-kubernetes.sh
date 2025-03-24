#!/bin/bash


# setup postgresql
echo "Setup postgresql"
kubectl apply -f kubernetes/postgresql-volume.yaml
kubectl apply -f kubernetes/postgresql-deployment.yaml
kubectl apply -f kubernetes/postgresql-service.yaml

# setup adres-app
echo "Setup adres-app"
kubectl apply -f kubernetes/adres-app-deployment.yaml
kubectl apply -f kubernetes/adres-app-service.yaml

kubectl apply -f kubernetes/prometheus-config.yaml
kubectl apply -f kubernetes/prometheus-deployment.yaml
kubectl apply -f kubernetes/prometheus-service.yaml


#
# https://grafana.com/tutorials/provision-dashboards-and-data-sources/
#
kubectl apply -f kubernetes/grafana-volume.yaml
kubectl apply -f kubernetes/grafana-secret.yaml
kubectl apply -f kubernetes/grafana-datasources.yaml
kubectl apply -f kubernetes/grafana-dashboard-provider.yaml
kubectl apply -f kubernetes/grafana-dashboards.yaml
kubectl apply -f kubernetes/grafana-deployment.yaml
kubectl apply -f kubernetes/grafana-service.yaml

kubectl apply -f kubernetes/angular-app-deployment.yaml
kubectl apply -f kubernetes/angular-app-service.yaml
