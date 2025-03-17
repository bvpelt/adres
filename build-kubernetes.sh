#!/bin/bash

# start minikube - after installing minikube and kubectl
# minikube start
# minikube dashboard

# create configmap for dashboards for grafana
kubectl create configmap spring-boot-dashboard --from-file=kubernetes/grafana-spring-boot.json
kubectl create configmap jvm-dashboard --from-file=kubernetes/grafana-jvm.json
kubectl create configmap prometheus-config --from-file=prometheus.yml=kubernetes/prometheus-config.yaml

# update configmaps
#kubectl create configmap spring-boot-dashboard --from-file=kubernetes/grafana-spring-boot.json --dry-run=client -o yaml | kubectl apply -f -
#kubectl create configmap jvm-dashboard --from-file=kubernetes/grafana-jvm.json --dry-run=client -o yaml | kubectl apply -f -
#kubectl create configmap prometheus-config --from-file=prometheus.yml=kubernetes/prometheus-config.yaml --dry-run=client -o yaml | kubectl apply -f -

# create volumes
kubectl apply -f kubernetes/postgresql-volume.yaml
kubectl apply -f kubernetes/grafana-volume.yaml

# create deployments
kubectl apply -f kubernetes/postgresql-deployment.yaml
kubectl apply -f kubernetes/postgresql-service.yaml

kubectl apply -f kubernetes/adres-app-deployment.yaml
kubectl apply -f kubernetes/adres-app-service.yaml

kubectl apply -f kubernetes/prometheus-deployment.yaml
kubectl apply -f kubernetes/prometheus-service.yaml

kubectl apply -f kubernetes/grafana-deployment.yaml
kubectl apply -f kubernetes/grafana-service.yaml

kubectl apply -f kubernetes/angular-app-deployment.yaml
kubectl apply -f kubernetes/angular-app-service.yaml
