# Helm

## Installing
See https://helm.sh/docs/intro/install/

```bash
curl https://baltocdn.com/helm/signing.asc | gpg --dearmor | sudo tee /usr/share/keyrings/helm.gpg > /dev/null
sudo apt-get install apt-transport-https --yes
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/helm.gpg] https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
sudo apt-get update
sudo apt-get install helm
```

## Monitor stack

To install a monitor stack for kubernetes / grafana
- add prometheus community helm repo
- install kube-prometheus-stack

### Add prometheus community helm repo

```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
"prometheus-community" has been added to your repositories

helm repo update
Hang tight while we grab the latest from your chart repositories...
...Successfully got an update from the "prometheus-community" chart repository
Update Complete. ⎈Happy Helming!⎈
```

### Install kube-prometheus-stack

```bash
helm install k8s-monitoring prometheus-community/kube-prometheus-stack --namespace monitoring --create-namespace
NAME: k8s-monitoring
LAST DEPLOYED: Wed Apr 16 18:39:12 2025
NAMESPACE: monitoring
STATUS: deployed
REVISION: 1
NOTES:
kube-prometheus-stack has been installed. Check its status by running:
  kubectl --namespace monitoring get pods -l "release=k8s-monitoring"

Get Grafana 'admin' user password by running:

  kubectl --namespace monitoring get secrets k8s-monitoring-grafana -o jsonpath="{.data.admin-password}" | base64 -d ; echo

Access Grafana local instance:

  export POD_NAME=$(kubectl --namespace monitoring get pod -l "app.kubernetes.io/name=grafana,app.kubernetes.io/instance=k8s-monitoring" -oname)
  kubectl --namespace monitoring port-forward $POD_NAME 3000

Visit https://github.com/prometheus-operator/kube-prometheus for instructions on how to create & configure Alertmanager and Prometheus instances using the Operator.


kubectl --namespace monitoring get pods -l "release=k8s-monitoring"
NAME                                                   READY   STATUS    RESTARTS   AGE
k8s-monitoring-kube-promet-operator-54f58dc9ff-8hxvs   1/1     Running   0          57s
k8s-monitoring-kube-state-metrics-66696d7db4-jkxwx     1/1     Running   0          57s
k8s-monitoring-prometheus-node-exporter-6ww7x          1/1     Running   0          57s

kubectl --namespace monitoring get secrets k8s-monitoring-grafana -o jsonpath="{.data.admin-password}" | base64 -d ; echo
prom-operator

kubectl --namespace monitoring get pod -l "app.kubernetes.io/name=grafana,app.kubernetes.io/instance=k8s-monitoring" -oname
pod/k8s-monitoring-grafana-648ddcf5db-6rx2v

kubectl get services --namespace=monitoring
NAME                                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
alertmanager-operated                     ClusterIP   None             <none>        9093/TCP,9094/TCP,9094/UDP   3m32s
k8s-monitoring-grafana                    ClusterIP   10.103.129.95    <none>        80/TCP                       3m44s
k8s-monitoring-kube-promet-alertmanager   ClusterIP   10.99.115.71     <none>        9093/TCP,8080/TCP            3m44s
k8s-monitoring-kube-promet-operator       ClusterIP   10.102.255.221   <none>        443/TCP                      3m44s
k8s-monitoring-kube-promet-prometheus     ClusterIP   10.104.78.210    <none>        9090/TCP,8080/TCP            3m44s
k8s-monitoring-kube-state-metrics         ClusterIP   10.97.8.244      <none>        8080/TCP                     3m44s
k8s-monitoring-prometheus-node-exporter   ClusterIP   10.104.183.186   <none>        9100/TCP                     3m44s
prometheus-operated                       ClusterIP   None             <none>        9090/TCP                     3m32s


export POD_NAME=$(kubectl --namespace monitoring get pod -l "app.kubernetes.io/name=grafana,app.kubernetes.io/instance=k8s-monitoring" -oname)
kubectl --namespace monitoring port-forward $POD_NAME 3000
Forwarding from 127.0.0.1:3000 -> 3000
Forwarding from [::1]:3000 -> 3000

```