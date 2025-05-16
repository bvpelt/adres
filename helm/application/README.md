# Application
The application has two helm charts
- the backand application adres-app
- the frontend application angular-app

## Adres-app

Verify the helm chart with the supplied values

```bash
helm template adres-app adres-app -f adres-app/values.yaml 
---
# Source: adres-app/templates/serviceaccount.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: adres-app
  labels:
    helm.sh/chart: adres-app-0.1.0
    app.kubernetes.io/name: adres-app
    app.kubernetes.io/instance: adres-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
automountServiceAccountToken: true
---
# Source: adres-app/templates/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: adres-app
  labels:
    helm.sh/chart: adres-app-0.1.0
    app.kubernetes.io/name: adres-app
    app.kubernetes.io/instance: adres-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
spec:
  type: NodePort
  ports:
    - name: http
      port: 8080
      targetPort: 8080
      protocol: TCP
    - name: actuator
      port: 8081
      targetPort: 8081
      protocol: TCP
  selector:
    app.kubernetes.io/name: adres-app
    app.kubernetes.io/instance: adres-app
---
# Source: adres-app/templates/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: adres-app
  labels:
    helm.sh/chart: adres-app-0.1.0
    app.kubernetes.io/name: adres-app
    app.kubernetes.io/instance: adres-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
spec:
  replicas: 1
  selector:
    matchLabels:
      app.kubernetes.io/name: adres-app
      app.kubernetes.io/instance: adres-app
  template:
    metadata:
      labels:
        helm.sh/chart: adres-app-0.1.0
        app.kubernetes.io/name: adres-app
        app.kubernetes.io/instance: adres-app
        app.kubernetes.io/version: "0.0.19"
        app.kubernetes.io/managed-by: Helm
    spec:
      serviceAccountName: adres-app
      containers:
        - name: adres-app
          image: "dockerpinguin/adres:0.0.19"
          imagePullPolicy: IfNotPresent
          ports:
            - name: http
              containerPort: 8080
              protocol: TCP
            - name: actuator
              containerPort: 8081
              protocol: TCP
          livenessProbe:
            failureThreshold: 3
            httpGet:
              path: /actuator/app-health
              port: 8081
            initialDelaySeconds: 10
            periodSeconds: 10
            timeoutSeconds: 5
          readinessProbe:
            failureThreshold: 3
            httpGet:
              path: /actuator/app-health
              port: 8081
            initialDelaySeconds: 5
            periodSeconds: 5
            timeoutSeconds: 3
          env:
            - name: SPRING_DATASOURCE_PASSWORD
              value: "12345"
            - name: SPRING_DATASOURCE_URL
              value: "jdbc:postgresql://postgres-postgresql.default.svc.cluster.local/adres"
            - name: SPRING_DATASOURCE_USERNAME
              value: "testuser"
            - name: SPRING_PROFILES_ACTIVE
              value: "runtime"
            - name: activeProfile
              value: "runtime"
---
# Source: adres-app/templates/tests/test-connection.yaml
apiVersion: v1
kind: Pod
metadata:
  name: "adres-app-test-connection"
  labels:
    helm.sh/chart: adres-app-0.1.0
    app.kubernetes.io/name: adres-app
    app.kubernetes.io/instance: adres-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
  annotations:
    "helm.sh/hook": test
spec:
  containers:
    - name: wget
      image: busybox
      command: ['wget']
      args: ['adres-app:']
  restartPolicy: Never

```

Install the helm chart

```bash
helm install adres-app adres-app -f adres-app/values.yaml 
NAME: adres-app
LAST DEPLOYED: Sat May 10 19:22:24 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
  export NODE_PORT=$(kubectl get --namespace default -o jsonpath="{.spec.ports[0].nodePort}" services adres-app)
  export NODE_IP=$(kubectl get nodes --namespace default -o jsonpath="{.items[0].status.addresses[0].address}")
  echo http://$NODE_IP:$NODE_PORT
  
# http://192.168.39.87:30717


helm test adres-app
NAME: adres-app
LAST DEPLOYED: Sat May 10 20:04:40 2025
NAMESPACE: default
STATUS: deployed
REVISION: 2
TEST SUITE:     adres-app-test-connection
Last Started:   Sat May 10 20:04:56 2025
Last Completed: Sat May 10 20:04:58 2025
Phase:          Succeeded
NOTES:
1. Get the application URL by running these commands:
  export NODE_PORT=$(kubectl get --namespace default -o jsonpath="{.spec.ports[0].nodePort}" services adres-app)
  export NODE_IP=$(kubectl get nodes --namespace default -o jsonpath="{.items[0].status.addresses[0].address}")
  echo http://$NODE_IP:$NODE_PORT
  

```

## Angular-app

Generate the helm chart

```bash
helm template angular-app angular-app -f angular-app/values.yaml 
---
# Source: angular-app/templates/serviceaccount.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: angular-app
  labels:
    helm.sh/chart: angular-app-0.2.0
    app.kubernetes.io/name: angular-app
    app.kubernetes.io/instance: angular-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
automountServiceAccountToken: true
---
# Source: angular-app/templates/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: angular-app
  labels:
    helm.sh/chart: angular-app-0.2.0
    app.kubernetes.io/name: angular-app
    app.kubernetes.io/instance: angular-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
spec:
  type: NodePort
  ports:
    - port: 80
      targetPort:  80
      protocol: TCP
      name: http
  selector:
    app.kubernetes.io/name: angular-app
    app.kubernetes.io/instance: angular-app
---
# Source: angular-app/templates/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: angular-app
  labels:
    helm.sh/chart: angular-app-0.2.0
    app.kubernetes.io/name: angular-app
    app.kubernetes.io/instance: angular-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
spec:
  replicas: 1
  selector:
    matchLabels:
      app.kubernetes.io/name: angular-app
      app.kubernetes.io/instance: angular-app
  template:
    metadata:
      labels:
        helm.sh/chart: angular-app-0.2.0
        app.kubernetes.io/name: angular-app
        app.kubernetes.io/instance: angular-app
        app.kubernetes.io/version: "0.0.19"
        app.kubernetes.io/managed-by: Helm
    spec:
      serviceAccountName: angular-app
      initContainers:
            - command:
              - sh
              - -c
              - until nc -z adres-app 8080; do echo waiting for adres-app; sleep 2; done;
              image: busybox
              name: wait-for-adres-app
      containers:
        - name: angular-app
          image: "dockerpinguin/adresbook:0.0.19"
          imagePullPolicy: IfNotPresent
          ports:
            - name: http
              containerPort: 80
              protocol: TCP
          env:
            - name: API_BASE_URL
              value: "http://adres-app:8080/adres/api/v1"
          livenessProbe:
            failureThreshold: 3
            httpGet:
              path: /
              port: 80
            initialDelaySeconds: 10
            periodSeconds: 10
            timeoutSeconds: 5
          readinessProbe:
            failureThreshold: 3
            httpGet:
              path: /
              port: 80
            initialDelaySeconds: 5
            periodSeconds: 5
            timeoutSeconds: 3
---
# Source: angular-app/templates/tests/test-connection.yaml
apiVersion: v1
kind: Pod
metadata:
  name: "angular-app-test-connection"
  labels:
    helm.sh/chart: angular-app-0.2.0
    app.kubernetes.io/name: angular-app
    app.kubernetes.io/instance: angular-app
    app.kubernetes.io/version: "0.0.19"
    app.kubernetes.io/managed-by: Helm
  annotations:
    "helm.sh/hook": test
spec:
  containers:
    - name: wget
      image: busybox
      command: ['wget']
      args: ['angular-app:80']
  restartPolicy: Never
```

Verify the helm chart

```bash
helm lint angular-app angular-app -f angular-app/values.yaml 
==> Linting angular-app
[INFO] Chart.yaml: icon is recommended

==> Linting angular-app
[INFO] Chart.yaml: icon is recommended

2 chart(s) linted, 0 chart(s) failed
```

Install the helm chart

```bash
helm install angular-app angular-app -f angular-app/values.yaml 
NAME: angular-app
LAST DEPLOYED: Fri May 16 20:32:55 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
  export NODE_PORT=$(kubectl get --namespace default -o jsonpath="{.spec.ports[0].nodePort}" services angular-app)
  export NODE_IP=$(kubectl get nodes --namespace default -o jsonpath="{.items[0].status.addresses[0].address}")
  echo http://$NODE_IP:$NODE_PORT

```