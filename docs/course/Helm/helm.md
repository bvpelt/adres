# Helm

Tutorial see https://youtu.be/jUYNS90nq8U?feature=shared&t=844 

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

# Helm Charts
See https://www.youtube.com/watch?v=jUYNS90nq8U 

## Create Helm structure
This can be done
- manual
- automated using helm

### Manual
The structure resembles
```text
webapp1
|-- Chart.yaml
|-- templates
|   |-- NOTES.txt
|   |-- configmap.yaml
|   |-- deployment.yaml
|   |-- service.yaml
|-- values-dev.yaml
|-- values-prod.yaml
|-- values-qa.yaml
|-- values.yaml
```

### Automated using helm
```bash
helm create adressapp
Creating adressapp
```
This generates
```text
adressapp
|-- charts
|-- Chart.yaml
|-- templates
|   |-- deployment.yaml
|   |-- _helpers.tpl
|   |-- hpa.yaml
|   |-- ingress.yaml
|   |-- NOTES.txt
|   |-- serviceaccount.yaml
|   |-- service.yaml
|   |--tests
|      |-- test-connection.yaml
|   |-- .helmignore
|-- values.yaml
```
### Customize
The generated helm chart contains a default values.yaml file with content
```yaml
# Default values for adressapp.
# This is a YAML-formatted file.
# Declare variables to be passed into your templates.

# This will set the replicaset count more information can be found here: https://kubernetes.io/docs/concepts/workloads/controllers/replicaset/
replicaCount: 1

# This sets the container image more information can be found here: https://kubernetes.io/docs/concepts/containers/images/
image:
  repository: nginx
  # This sets the pull policy for images.
  pullPolicy: IfNotPresent
  # Overrides the image tag whose default is the chart appVersion.
  tag: ""

# This is for the secrets for pulling an image from a private repository more information can be found here: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/
imagePullSecrets: []
# This is to override the chart name.
nameOverride: ""
fullnameOverride: ""

# This section builds out the service account more information can be found here: https://kubernetes.io/docs/concepts/security/service-accounts/
serviceAccount:
  # Specifies whether a service account should be created
  create: true
  # Automatically mount a ServiceAccount's API credentials?
  automount: true
  # Annotations to add to the service account
  annotations: {}
  # The name of the service account to use.
  # If not set and create is true, a name is generated using the fullname template
  name: ""

# This is for setting Kubernetes Annotations to a Pod.
# For more information checkout: https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/
podAnnotations: {}
# This is for setting Kubernetes Labels to a Pod.
# For more information checkout: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/
podLabels: {}

podSecurityContext: {}
  # fsGroup: 2000

securityContext: {}
  # capabilities:
  #   drop:
  #   - ALL
  # readOnlyRootFilesystem: true
  # runAsNonRoot: true
  # runAsUser: 1000

# This is for setting up a service more information can be found here: https://kubernetes.io/docs/concepts/services-networking/service/
service:
  # This sets the service type more information can be found here: https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services-service-types
  type: ClusterIP
  # This sets the ports more information can be found here: https://kubernetes.io/docs/concepts/services-networking/service/#field-spec-ports
  port: 80

# This block is for setting up the ingress for more information can be found here: https://kubernetes.io/docs/concepts/services-networking/ingress/
ingress:
  enabled: false
  className: ""
  annotations: {}
    # kubernetes.io/ingress.class: nginx
    # kubernetes.io/tls-acme: "true"
  hosts:
    - host: chart-example.local
      paths:
        - path: /
          pathType: ImplementationSpecific
  tls: []
  #  - secretName: chart-example-tls
  #    hosts:
  #      - chart-example.local

resources: {}
  # We usually recommend not to specify default resources and to leave this as a conscious
  # choice for the user. This also increases chances charts run on environments with little
  # resources, such as Minikube. If you do want to specify resources, uncomment the following
  # lines, adjust them as necessary, and remove the curly braces after 'resources:'.
  # limits:
  #   cpu: 100m
  #   memory: 128Mi
  # requests:
  #   cpu: 100m
  #   memory: 128Mi

# This is to setup the liveness and readiness probes more information can be found here: https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/
livenessProbe:
  httpGet:
    path: /
    port: http
readinessProbe:
  httpGet:
    path: /
    port: http

# This section is for setting up autoscaling more information can be found here: https://kubernetes.io/docs/concepts/workloads/autoscaling/
autoscaling:
  enabled: false
  minReplicas: 1
  maxReplicas: 100
  targetCPUUtilizationPercentage: 80
  # targetMemoryUtilizationPercentage: 80

# Additional volumes on the output Deployment definition.
volumes: []
# - name: foo
#   secret:
#     secretName: mysecret
#     optional: false

# Additional volumeMounts on the output Deployment definition.
volumeMounts: []
# - name: foo
#   mountPath: "/etc/foo"
#   readOnly: true

nodeSelector: {}

tolerations: []

affinity: {}
```
Instead of using this file remove everything and start from scratch - to build understanding.

The charts directory contains dependencies of other helm charts.
The templates directory contains the user configuration where one puts the kubernetes definitions. After using the automated creating of the helm chart the
template directory contains templates. Which will not be used here.
Instead a
- configmap.yaml
- deployment.yaml
- service.yaml 
is created here.

From the docs/course/Helm directory start the helm chart
```bash
helm install myadresapp adressapp/
NAME: myadresapp
LAST DEPLOYED: Sat Apr 19 20:06:21 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None

# Check deployment
kubectl get all
NAME                                READY   STATUS    RESTARTS   AGE
pod/mydeployment-74dcc9446c-f7xw2   1/1     Running   0          9s
pod/mydeployment-74dcc9446c-jqhs9   1/1     Running   0          9s
pod/mydeployment-74dcc9446c-kf8qh   1/1     Running   0          9s
pod/mydeployment-74dcc9446c-mxwlg   1/1     Running   0          9s
pod/mydeployment-74dcc9446c-wvflr   1/1     Running   0          9s

NAME                 TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)        AGE
service/kubernetes   ClusterIP      10.96.0.1       <none>        443/TCP        17d
service/mywebapp     LoadBalancer   10.108.119.34   <pending>     80:30769/TCP   9s

NAME                           READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/mydeployment   5/5     5            5           9s

NAME                                      DESIRED   CURRENT   READY   AGE
replicaset.apps/mydeployment-74dcc9446c   5         5         5       9s


# Test application
## Tunnel the service
minikube service mywebapp
|-----------|----------|-------------|----------------------------|
| NAMESPACE |   NAME   | TARGET PORT |            URL             |
|-----------|----------|-------------|----------------------------|
| default   | mywebapp | flask/80    | http://192.168.39.87:32512 |
|-----------|----------|-------------|----------------------------|
🎉  Opening service default/mywebapp in default browser...

## Opens the browser at http://192.168.39.87:32512/ 

# testing

n=5000  # Set how many times you want to loop
command="curl http://192.168.39.87:32512/" 

for ((i=1; i<=n; i++)); do
  echo "Iteration $i:"
  eval "$command"
done


# Delete help chart

helm delete myadresapp
release "myadresapp" uninstalled

kubectl get all
NAME                                READY   STATUS        RESTARTS   AGE
pod/mydeployment-74dcc9446c-2r96x   1/1     Terminating   0          30m
pod/mydeployment-74dcc9446c-6gsc7   1/1     Terminating   0          30m
pod/mydeployment-74dcc9446c-d6shj   1/1     Terminating   0          30m
pod/mydeployment-74dcc9446c-qvwhl   1/1     Terminating   0          30m
pod/mydeployment-74dcc9446c-wj6tg   1/1     Terminating   0          30m

NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   17d

kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   17d

```

### Templates
Use same set as in adresapp -> adresapp-02 and add templating. And add a value for the application name.

```bash
helm install myadresapp-02 adressapp-02/ --values=adressapp-02/values.yaml
NAME: myadresapp-02
LAST DEPLOYED: Sat Apr 19 20:51:39 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None

kubectl get all
NAME                             READY   STATUS    RESTARTS   AGE
pod/myhelpapp-5f98546d8d-db7hq   1/1     Running   0          100s
pod/myhelpapp-5f98546d8d-hvfs5   1/1     Running   0          100s
pod/myhelpapp-5f98546d8d-jhb8c   1/1     Running   0          100s
pod/myhelpapp-5f98546d8d-jnrr8   1/1     Running   0          100s
pod/myhelpapp-5f98546d8d-t4vzl   1/1     Running   0          100s

NAME                 TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
service/kubernetes   ClusterIP      10.96.0.1      <none>        443/TCP        17d
service/myhelpapp    LoadBalancer   10.101.205.0   <pending>     80:30520/TCP   100s

NAME                        READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/myhelpapp   5/5     5            5           100s

NAME                                   DESIRED   CURRENT   READY   AGE
replicaset.apps/myhelpapp-5f98546d8d   5         5         5       100s


helm ls
NAME         	NAMESPACE	REVISION	UPDATED                                 	STATUS  	CHART          	APP VERSION
myadresapp-02	default  	1       	2025-04-19 20:51:39.754977086 +0200 CEST	deployed	adressapp-0.1.0	1.16.0    
```

After adding more Values redeploy the application

```bash
helm upgrade myadresapp-02 adressapp-02/ --values=adressapp-02/values.yaml
Release "myadresapp-02" has been upgraded. Happy Helming!
NAME: myadresapp-02
LAST DEPLOYED: Sat Apr 19 21:08:45 2025
NAMESPACE: default
STATUS: deployed
REVISION: 2
TEST SUITE: None

helm ls
NAME         	NAMESPACE	REVISION	UPDATED                                 	STATUS  	CHART          	APP VERSION
myadresapp-02	default  	2       	2025-04-19 21:08:45.690781389 +0200 CEST	deployed	adressapp-0.1.0	1.16.0   
```
