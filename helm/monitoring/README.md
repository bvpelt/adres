# Monitoring environment

Using bitnami grafana the installation from adres/helm directory exists of:

```bash
kubectl create namespace monitoring
helm install prometheus \
    --namespace monitoring \
    oci://registry-1.docker.io/bitnamicharts/prometheus
helm install grafana \
    --values values.yaml \
    --namespace monitoring \
    oci://registry-1.docker.io/bitnamicharts/grafana
```
## Prometheus
Generated kubernetes config for prometheus

```bash
helm template prometheus     --namespace monitoring     oci://registry-1.docker.io/bitnamicharts/prometheus
Pulled: registry-1.docker.io/bitnamicharts/prometheus:2.0.3
Digest: sha256:c37e8bfd3c2ec3bfab06687bf1868b223f8eee8335805f615fefeb1f6237ecad
---
# Source: prometheus/templates/alertmanager/networkpolicy.yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: prometheus-alertmanager 
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
spec:
  podSelector:
    matchLabels:
      app.kubernetes.io/instance: prometheus
      app.kubernetes.io/name: prometheus
      app.kubernetes.io/part-of: prometheus
      app.kubernetes.io/component: alertmanager
  policyTypes:
    - Ingress
    - Egress
  egress:
    - {}
  ingress:
    - ports:
        - port: 9093
        - port: 80
---
# Source: prometheus/templates/server/networkpolicy.yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: prometheus-server
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
spec:
  podSelector:
    matchLabels:
      app.kubernetes.io/instance: prometheus
      app.kubernetes.io/name: prometheus
      app.kubernetes.io/part-of: prometheus
      app.kubernetes.io/component: server
  policyTypes:
    - Ingress
    - Egress
  egress:
    - {}
  ingress:
    - ports:
        - port: 9090
        - port: 80
---
# Source: prometheus/templates/alertmanager/pdb.yaml
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: "prometheus-alertmanager"
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 0.28.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
spec:
  maxUnavailable: 1
  selector:
    matchLabels:
      app.kubernetes.io/instance: prometheus
      app.kubernetes.io/name: prometheus
      app.kubernetes.io/part-of: prometheus
      app.kubernetes.io/component: alertmanager
---
# Source: prometheus/templates/server/pdb.yaml
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: prometheus-server
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
spec:
  maxUnavailable: 1
  selector:
    matchLabels:
      app.kubernetes.io/instance: prometheus
      app.kubernetes.io/name: prometheus
      app.kubernetes.io/part-of: prometheus
      app.kubernetes.io/component: server
---
# Source: prometheus/templates/alertmanager/service-account.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: "prometheus-alertmanager"
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 0.28.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
automountServiceAccountToken: false
---
# Source: prometheus/templates/server/service-account.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: prometheus-server
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
automountServiceAccountToken: false
---
# Source: prometheus/templates/alertmanager/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: "prometheus-alertmanager"
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 0.28.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
data:
  alertmanager.yaml:
    |
      receivers:
        - name: default-receiver
      route:
        group_wait: 10s
        group_interval: 5m
        receiver: default-receiver
        repeat_interval: 3h
---
# Source: prometheus/templates/server/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: prometheus-server
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
data:
  prometheus.yaml:
    |
      global:
        external_labels:
          monitor: prometheus
      scrape_configs:
        - job_name: prometheus
          kubernetes_sd_configs:
            - role: endpoints
              namespaces:
                names:
                - monitoring
          metrics_path: /metrics
          relabel_configs:
            - source_labels:
                - job
              target_label: __tmp_prometheus_job_name
            - action: keep
              source_labels:
                - __meta_kubernetes_service_label_app_kubernetes_io_component
                - __meta_kubernetes_service_labelpresent_app_kubernetes_io_component
              regex: (server);true
            - action: keep
              source_labels:
                - __meta_kubernetes_service_label_app_kubernetes_io_instance
                - __meta_kubernetes_service_labelpresent_app_kubernetes_io_instance
              regex: (prometheus);true
            - action: keep
              source_labels:
                - __meta_kubernetes_service_label_app_kubernetes_io_name
                - __meta_kubernetes_service_labelpresent_app_kubernetes_io_name
              regex: (prometheus);true
            - action: keep
              source_labels:
                - __meta_kubernetes_endpoint_port_name
              regex: http
            - source_labels:
                - __meta_kubernetes_endpoint_address_target_kind
                - __meta_kubernetes_endpoint_address_target_name
              separator: ;
              regex: Node;(.*)
              replacement: ${1}
              target_label: node
            - source_labels:
                - __meta_kubernetes_endpoint_address_target_kind
                - __meta_kubernetes_endpoint_address_target_name
              separator: ;
              regex: Pod;(.*)
              replacement: ${1}
              target_label: pod
            - source_labels:
                - __meta_kubernetes_namespace
              target_label: namespace
            - source_labels:
                - __meta_kubernetes_service_name
              target_label: service
            - source_labels:
                - __meta_kubernetes_pod_name
              target_label: pod
            - source_labels:
                - __meta_kubernetes_pod_container_name
              target_label: container
            - action: drop
              source_labels:
                - __meta_kubernetes_pod_phase
              regex: (Failed|Succeeded)
            - source_labels:
                - __meta_kubernetes_service_name
              target_label: job
              replacement: ${1}
            - target_label: endpoint
              replacement: http
            - source_labels:
                - __address__
              target_label: __tmp_hash
              modulus: 1
              action: hashmod
            - source_labels:
                - __tmp_hash
              regex: 0
              action: keep
        - job_name: alertmanager
          kubernetes_sd_configs:
            - role: endpoints
              namespaces:
                names:
                - monitoring
          metrics_path: /metrics
          relabel_configs:
            - source_labels:
                - job
              target_label: __tmp_prometheus_job_name
            - action: keep
              source_labels:
                - __meta_kubernetes_service_label_app_kubernetes_io_component
                - __meta_kubernetes_service_labelpresent_app_kubernetes_io_component
              regex: (alertmanager);true
            - action: keep
              source_labels:
                - __meta_kubernetes_service_label_app_kubernetes_io_instance
                - __meta_kubernetes_service_labelpresent_app_kubernetes_io_instance
              regex: (prometheus);true
            - action: keep
              source_labels:
                - __meta_kubernetes_service_label_app_kubernetes_io_name
                - __meta_kubernetes_service_labelpresent_app_kubernetes_io_name
              regex: (prometheus);true
            - action: keep
              source_labels:
                - __meta_kubernetes_endpoint_port_name
              regex: http
            - source_labels:
                - __meta_kubernetes_endpoint_address_target_kind
                - __meta_kubernetes_endpoint_address_target_name
              separator: ;
              regex: Node;(.*)
              replacement: ${1}
              target_label: node
            - source_labels:
                - __meta_kubernetes_endpoint_address_target_kind
                - __meta_kubernetes_endpoint_address_target_name
              separator: ;
              regex: Pod;(.*)
              replacement: ${1}
              target_label: pod
            - source_labels:
                - __meta_kubernetes_namespace
              target_label: namespace
            - source_labels:
                - __meta_kubernetes_service_name
              target_label: service
            - source_labels:
                - __meta_kubernetes_pod_name
              target_label: pod
            - source_labels:
                - __meta_kubernetes_pod_container_name
              target_label: container
            - action: drop
              source_labels:
                - __meta_kubernetes_pod_phase
              regex: (Failed|Succeeded)
            - source_labels:
                - __meta_kubernetes_service_name
              target_label: job
              replacement: ${1}
            - target_label: endpoint
              replacement: http
            - source_labels:
                - __address__
              target_label: __tmp_hash
              modulus: 1
              action: hashmod
            - source_labels:
                - __tmp_hash
              regex: 0
              action: keep
      alerting:
        alertmanagers:
          - scheme: HTTP
            static_configs:
              - targets: [ "prometheus-alertmanager.monitoring.svc.cluster.local:80" ]
      rule_files:
        - rules.yaml
  rules.yaml:
    '{}'
---
# Source: prometheus/templates/server/clusterrole.yaml
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: prometheus-monitoring-server
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
rules:
  # These rules come from <https://github.com/prometheus-community/helm-charts/blob/main/charts/prometheus/templates/clusterrole.yaml>
  - apiGroups:
      - ""
    resources:
      - nodes
      - nodes/proxy
      - nodes/metrics
      - services
      - endpoints
      - pods
      - ingresses
      - configmaps
    verbs:
      - get
      - list
      - watch
  - apiGroups:
      - "extensions"
      - "networking.k8s.io"
    resources:
      - ingresses/status
      - ingresses
    verbs:
      - get
      - list
      - watch
  - apiGroups:
      - "discovery.k8s.io"
    resources:
      - endpointslices
    verbs:
      - get
      - list
      - watch
  - nonResourceURLs:
      - "/metrics"
    verbs:
      - get
---
# Source: prometheus/templates/server/clusterrolebinding.yaml
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: prometheus-monitoring-server
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: prometheus-monitoring-server
subjects:
  - kind: ServiceAccount
    name: prometheus-server
    namespace: "monitoring"
---
# Source: prometheus/templates/alertmanager/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: "prometheus-alertmanager"
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 0.28.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
spec:
  type: LoadBalancer
  sessionAffinity: None
  externalTrafficPolicy: "Cluster"
  ports:
    - name: http
      port: 80
      protocol: TCP
      targetPort: http
  selector:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
---
# Source: prometheus/templates/server/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: prometheus-server
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
spec:
  type: LoadBalancer
  sessionAffinity: ClientIP
  externalTrafficPolicy: "Cluster"
  ports:
    - name: http
      port: 80
      targetPort: http
      protocol: TCP
  selector:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
---
# Source: prometheus/templates/server/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: prometheus-server
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 3.3.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: server
spec:
  replicas: 1
  strategy:
    type: RollingUpdate
  selector:
    matchLabels:
      app.kubernetes.io/instance: prometheus
      app.kubernetes.io/name: prometheus
      app.kubernetes.io/part-of: prometheus
      app.kubernetes.io/component: server
  template:
    metadata:
      annotations:
        checksum/configmap: bdae4a4267bf10627f061290129a5e54f01aa7f73d0a8dbe6d23656a79d75730
      labels:
        app.kubernetes.io/instance: prometheus
        app.kubernetes.io/managed-by: Helm
        app.kubernetes.io/name: prometheus
        app.kubernetes.io/version: 3.3.1
        helm.sh/chart: prometheus-2.0.3
        app.kubernetes.io/part-of: prometheus
        app.kubernetes.io/component: server
    spec:
      serviceAccountName: prometheus-server
      
      automountServiceAccountToken: true
      affinity:
        podAffinity:
          
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - podAffinityTerm:
                labelSelector:
                  matchLabels:
                    app.kubernetes.io/instance: prometheus
                    app.kubernetes.io/name: prometheus
                    app.kubernetes.io/component: server
                topologyKey: kubernetes.io/hostname
              weight: 1
        nodeAffinity:
          
      securityContext:
        fsGroup: 1001
        fsGroupChangePolicy: Always
        supplementalGroups: []
        sysctls: []
      initContainers:
      containers:
        - name: prometheus
          image: docker.io/bitnami/prometheus:3.3.1-debian-12-r1
          imagePullPolicy: IfNotPresent
          securityContext:
            allowPrivilegeEscalation: false
            capabilities:
              drop:
              - ALL
            privileged: false
            readOnlyRootFilesystem: true
            runAsGroup: 1001
            runAsNonRoot: true
            runAsUser: 1001
            seLinuxOptions: {}
            seccompProfile:
              type: RuntimeDefault
          args:
            - "--config.file=/opt/bitnami/prometheus/conf/prometheus.yaml"
            - "--storage.tsdb.path=/bitnami/prometheus/data"
            - "--storage.tsdb.retention.time=10d"
            - "--storage.tsdb.retention.size=0"
            - "--log.level=info"
            - "--log.format=logfmt"
            - "--web.listen-address=:9090"
            - "--web.console.libraries=/opt/bitnami/prometheus/conf/console_libraries"
            - "--web.console.templates=/opt/bitnami/prometheus/conf/consoles"
            - "--web.route-prefix=/"
          env:
          envFrom:
          resources:
            limits:
              cpu: 150m
              ephemeral-storage: 2Gi
              memory: 192Mi
            requests:
              cpu: 100m
              ephemeral-storage: 50Mi
              memory: 128Mi
          ports:
            - name: http
              containerPort: 9090
          livenessProbe:
            failureThreshold: 3
            initialDelaySeconds: 5
            periodSeconds: 20
            successThreshold: 1
            timeoutSeconds: 3
            httpGet:
              path: /-/healthy
              port: http
          readinessProbe:
            failureThreshold: 5
            initialDelaySeconds: 5
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 2
            httpGet:
              path: /-/ready
              port: http
          volumeMounts:
            - name: empty-dir
              mountPath: /tmp
              subPath: tmp-dir
            - name: config
              mountPath: /opt/bitnami/prometheus/conf
              readOnly: true
            - name: data
              mountPath: /bitnami/prometheus/data
      volumes:
        - name: empty-dir
          emptyDir: {}
        - name: config
          configMap:
            name: prometheus-server
        - name: data
          emptyDir: {}
---
# Source: prometheus/templates/alertmanager/statefulset.yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: "prometheus-alertmanager"
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: prometheus
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: prometheus
    app.kubernetes.io/version: 0.28.1
    helm.sh/chart: prometheus-2.0.3
    app.kubernetes.io/part-of: prometheus
    app.kubernetes.io/component: alertmanager
spec:
  replicas: 1
  podManagementPolicy: "OrderedReady"
  selector:
    matchLabels:
      app.kubernetes.io/instance: prometheus
      app.kubernetes.io/name: prometheus
      app.kubernetes.io/part-of: prometheus
      app.kubernetes.io/component: alertmanager
  serviceName: prometheus-alertmanager-headless
  updateStrategy:
    type: RollingUpdate
  template:
    metadata:
      labels:
        app.kubernetes.io/instance: prometheus
        app.kubernetes.io/managed-by: Helm
        app.kubernetes.io/name: prometheus
        app.kubernetes.io/version: 0.28.1
        helm.sh/chart: prometheus-2.0.3
        app.kubernetes.io/part-of: prometheus
        app.kubernetes.io/component: alertmanager
    spec:
      serviceAccountName: prometheus-alertmanager
      
      automountServiceAccountToken: false
      affinity:
        podAffinity:
          
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - podAffinityTerm:
                labelSelector:
                  matchLabels:
                    app.kubernetes.io/instance: prometheus
                    app.kubernetes.io/name: prometheus
                    app.kubernetes.io/component: alertmanager
                topologyKey: kubernetes.io/hostname
              weight: 1
        nodeAffinity:
          
      securityContext:
        fsGroup: 1001
        fsGroupChangePolicy: Always
        supplementalGroups: []
        sysctls: []
      initContainers:
      containers:
        - name: alertmanager
          image: docker.io/bitnami/alertmanager:0.28.1-debian-12-r5
          imagePullPolicy: IfNotPresent
          securityContext:
            allowPrivilegeEscalation: false
            capabilities:
              drop:
              - ALL
            privileged: false
            readOnlyRootFilesystem: true
            runAsGroup: 1001
            runAsNonRoot: true
            runAsUser: 1001
            seLinuxOptions: {}
            seccompProfile:
              type: RuntimeDefault
          args:
            - "--config.file=/opt/bitnami/alertmanager/conf/alertmanager.yaml"
            - "--storage.path=/opt/bitnami/alertmanager/data"
            - "--web.listen-address=0.0.0.0:9093"
          env:
            - name: POD_IP
              valueFrom:
                fieldRef:
                  apiVersion: v1
                  fieldPath: status.podIP
          envFrom:
          resources:
            limits:
              cpu: 150m
              ephemeral-storage: 2Gi
              memory: 192Mi
            requests:
              cpu: 100m
              ephemeral-storage: 50Mi
              memory: 128Mi
          ports:
            - name: http
              containerPort: 9093
            - name: tcp-cluster
              containerPort: 9094
              protocol: TCP
            - name: udp-cluster
              containerPort: 9094
              protocol: UDP
          livenessProbe:
            failureThreshold: 3
            initialDelaySeconds: 5
            periodSeconds: 20
            successThreshold: 1
            timeoutSeconds: 3
            httpGet:
              path: /-/healthy
              port: http
          readinessProbe:
            failureThreshold: 5
            initialDelaySeconds: 5
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 2
            httpGet:
              path: /-/ready
              port: http
          volumeMounts:
            - name: empty-dir
              mountPath: /tmp
              subPath: tmp-dir
            - name: config
              mountPath: /opt/bitnami/alertmanager/conf
              readOnly: true
            - name: data
              mountPath: /bitnami/alertmanager/data
      volumes:
        - name: empty-dir
          emptyDir: {}
        - name: config
          configMap:
            name: prometheus-alertmanager
        - name: data
          emptyDir: {}
```

Result of prometheus installation

```bash
helm install prometheus \
    --namespace monitoring \
    oci://registry-1.docker.io/bitnamicharts/prometheus
Pulled: registry-1.docker.io/bitnamicharts/prometheus:2.0.3
Digest: sha256:c37e8bfd3c2ec3bfab06687bf1868b223f8eee8335805f615fefeb1f6237ecad
NAME: prometheus
LAST DEPLOYED: Wed May  7 20:46:33 2025
NAMESPACE: monitoring
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: prometheus
CHART VERSION: 2.0.3
APP VERSION: 3.3.1

Did you know there are enterprise versions of the Bitnami catalog? For enhanced secure software supply chain features, unlimited pulls from Docker, LTS support, or application customization, see Bitnami Premium or Tanzu Application Catalog. See https://www.arrow.com/globalecs/na/vendors/bitnami for more information.

** Please be patient while the chart is being deployed **

Prometheus can be accessed via port "80" on the following DNS name from within your cluster:

    prometheus-server.monitoring.svc.cluster.local

To access Prometheus from outside the cluster execute the following commands:

  NOTE: It may take a few minutes for the LoadBalancer IP to be available.
        Watch the status with: 'kubectl get svc --namespace monitoring -w prometheus'

    export SERVICE_IP=$(kubectl get svc --namespace monitoring prometheus --template "{{ range (index .status.loadBalancer.ingress 0) }}{{ . }}{{ end }}")
    echo "Prometheus URL: http://$SERVICE_IP/"

Watch the Alertmanager StatefulSet status using the command:

    kubectl get sts -w --namespace monitoring -l app.kubernetes.io/name=prometheus-alertmanager,app.kubernetes.io/instance=prometheus

Alertmanager can be accessed via port "80" on the following DNS name from within your cluster:

    prometheus-alertmanager.monitoring.svc.cluster.local

To access Alertmanager from outside the cluster execute the following commands:

  NOTE: It may take a few minutes for the LoadBalancer IP to be available.
        Watch the status with: 'kubectl get svc --namespace monitoring -w prometheus-alertmanager'

    export SERVICE_IP=$(kubectl get svc --namespace monitoring prometheus-alertmanager --template "{{ range (index .status.loadBalancer.ingress 0) }}{{ . }}{{ end }}")
    echo "Alertmanager URL: http://$SERVICE_IP/"

WARNING: There are "resources" sections in the chart not set. Using "resourcesPreset" is not recommended for production. For production installations, please set the following values according to your workload needs:
  - alertmanager.resources
  - server.resources
  - server.thanos.resources
+info https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/

```

## Grafana

Generated kubernetes config for grafana 

```bash
helm template grafana \
    --values monitoring/values.yaml \
    --namespace monitoring \
    oci://registry-1.docker.io/bitnamicharts/grafana
Pulled: registry-1.docker.io/bitnamicharts/grafana:11.6.7
Digest: sha256:cea0a30eb1c010ea7226fdff9505e23b7e8a0000e4e2ed89457b5acca023596a
---
# Source: grafana/templates/networkpolicy.yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: grafana
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
spec:
  podSelector:
    matchLabels:
      app.kubernetes.io/instance: grafana
      app.kubernetes.io/name: grafana
      app.kubernetes.io/component: grafana
  policyTypes:
    - Ingress
    - Egress
  egress:
    - {}
  ingress:
    - ports:
        - port: 3000
---
# Source: grafana/templates/pdb.yaml
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: grafana
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
spec:
  maxUnavailable: 1
  selector:
    matchLabels:
      app.kubernetes.io/instance: grafana
      app.kubernetes.io/name: grafana
      app.kubernetes.io/component: grafana
---
# Source: grafana/templates/serviceaccount.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: grafana
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
secrets:
  - name: grafana-admin
  - name: grafana-datasources
automountServiceAccountToken: false
---
# Source: grafana/templates/datasources-secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: grafana-datasources
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
type: Opaque
data:
  datasources.yaml: 
    YXBpVmVyc2lvbjogMQpkYXRhc291cmNlczoKLSBhY2Nlc3M6IHByb3h5CiAgZWRpdGFibGU6IHRydWUKICBpc0RlZmF1bHQ6IHRydWUKICBuYW1lOiBQcm9tZXRoZXVzCiAgb3JnSWQ6IDEKICB0eXBlOiBwcm9tZXRoZXVzCiAgdXJsOiBodHRwOi8vcHJvbWV0aGV1cy5tb25pdG9yaW5nLnN2Yy5jbHVzdGVyLmxvY2FsCiAgdmVyc2lvbjogMQotIGFjY2VzczogcHJveHkKICBlZGl0YWJsZTogdHJ1ZQogIG5hbWU6IEFsZXJ0bWFuYWdlcgogIG9yZ0lkOiAxCiAgdHlwZTogYWxlcnRtYW5hZ2VyCiAgdWlkOiBhbGVydG1hbmFnZXIKICB1cmw6IGh0dHA6Ly9wcm9tZXRoZXVzLWFsZXJ0bWFuYWdlci5tb25pdG9yaW5nLnN2Yy5jbHVzdGVyLmxvY2FsOjkwOTMKICB2ZXJzaW9uOiAx
---
# Source: grafana/templates/secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: grafana-admin
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
type: Opaque
data:
  GF_SECURITY_ADMIN_PASSWORD: "YzJDTGhUcnVvbQ=="
---
# Source: grafana/templates/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: grafana-envvars
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
data:
  GF_SECURITY_ADMIN_USER: "admin"
  GF_INSTALL_PLUGINS: ""
  GF_PATHS_PLUGINS: "/opt/bitnami/grafana/data/plugins"
  GF_AUTH_LDAP_ENABLED: "false"
  GF_AUTH_LDAP_CONFIG_FILE: "/opt/bitnami/grafana/conf/ldap.toml"
  GF_AUTH_LDAP_ALLOW_SIGN_UP: "false"
  GF_PATHS_PROVISIONING: "/opt/bitnami/grafana/conf/provisioning"
  GF_PATHS_CONFIG: "/opt/bitnami/grafana/conf/grafana.ini"
  GF_PATHS_DATA: "/opt/bitnami/grafana/data"
  GF_PATHS_LOGS: "/opt/bitnami/grafana/logs"
---
# Source: grafana/templates/pvc.yaml
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: grafana
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
spec:
  accessModes:
    - "ReadWriteOnce"
  resources:
    requests:
      storage: "10Gi"
---
# Source: grafana/templates/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: grafana
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
spec:
  type: ClusterIP
  sessionAffinity: None
  ports:
    - port: 3000
      targetPort: dashboard
      protocol: TCP
      name: http
      nodePort: null
  selector:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/name: grafana
    app.kubernetes.io/component: grafana
---
# Source: grafana/templates/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: grafana
  namespace: "monitoring"
  labels:
    app.kubernetes.io/instance: grafana
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: grafana
    app.kubernetes.io/version: 11.6.1
    helm.sh/chart: grafana-11.6.7
    app.kubernetes.io/component: grafana
spec:
  replicas: 1
  selector:
    matchLabels:
      app.kubernetes.io/instance: grafana
      app.kubernetes.io/name: grafana
      app.kubernetes.io/component: grafana
  strategy: 
    type: RollingUpdate
  template:
    metadata:
      labels:
        app.kubernetes.io/instance: grafana
        app.kubernetes.io/managed-by: Helm
        app.kubernetes.io/name: grafana
        app.kubernetes.io/version: 11.6.1
        helm.sh/chart: grafana-11.6.7
        app.kubernetes.io/component: grafana
      annotations:
        checksum/secret: 93cac2e19e18e8d2a9142ee860ca9bfec9cf5b6b4551b7eb3bf7cb3a5888408a
        checksum/config: 29876e5245f4d0f4b0764704b02ba4b2ed2fa1843e1073bc38de18e612b171c7
        checksum/dashboard-provider: 01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b
    spec:
      
      automountServiceAccountToken: false
      serviceAccountName: grafana
      affinity:
        podAffinity:
          
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - podAffinityTerm:
                labelSelector:
                  matchLabels:
                    app.kubernetes.io/instance: grafana
                    app.kubernetes.io/name: grafana
                    app.kubernetes.io/component: grafana
                topologyKey: kubernetes.io/hostname
              weight: 1
        nodeAffinity:
          
      securityContext:
        fsGroup: 1001
        fsGroupChangePolicy: Always
        supplementalGroups: []
        sysctls: []
      enableServiceLinks: true
      initContainers:
      containers:
        - name: grafana
          image: docker.io/bitnami/grafana:11.6.1-debian-12-r1
          imagePullPolicy: IfNotPresent
          securityContext:
            allowPrivilegeEscalation: false
            capabilities:
              drop:
              - ALL
            privileged: false
            readOnlyRootFilesystem: true
            runAsGroup: 1001
            runAsNonRoot: true
            runAsUser: 1001
            seLinuxOptions: {}
            seccompProfile:
              type: RuntimeDefault
          envFrom:
            - configMapRef:
                name: grafana-envvars
          env:
            - name: GF_SECURITY_ADMIN_PASSWORD_FILE
              value: /opt/bitnami/grafana/secrets/GF_SECURITY_ADMIN_PASSWORD
          volumeMounts:
            - name: empty-dir
              mountPath: /tmp
              subPath: tmp-dir
            - name: empty-dir
              mountPath: /opt/bitnami/grafana/conf
              subPath: app-conf-dir
            - name: empty-dir
              mountPath: /opt/bitnami/grafana/tmp
              subPath: app-tmp-dir
            - name: empty-dir
              mountPath: /bitnami/grafana
              subPath: app-volume-dir
            - name: grafana-secrets
              mountPath: /opt/bitnami/grafana/secrets
            - name: data
              mountPath: /opt/bitnami/grafana/data
            - name: datasources
              mountPath: /opt/bitnami/grafana/conf.default/provisioning/datasources
          ports:
            - name: dashboard
              containerPort: 3000
              protocol: TCP
          livenessProbe:
            tcpSocket:
              port: dashboard
            initialDelaySeconds: 120
            periodSeconds: 10
            timeoutSeconds: 5
            successThreshold: 1
            failureThreshold: 6
          readinessProbe:
            httpGet:
              path: /api/health
              port: dashboard
              scheme: HTTP
            initialDelaySeconds: 30
            periodSeconds: 10
            timeoutSeconds: 5
            successThreshold: 1
            failureThreshold: 6
          resources:
            limits:
              cpu: 150m
              ephemeral-storage: 2Gi
              memory: 192Mi
            requests:
              cpu: 100m
              ephemeral-storage: 50Mi
              memory: 128Mi
      volumes:
        - name: empty-dir
          emptyDir: {}
        - name: grafana-secrets
          projected:
            sources:
              - secret:
                  name:  grafana-admin
        - name: data
          persistentVolumeClaim:
            claimName: grafana
        - name: datasources
          secret:
            secretName: grafana-datasources

```

Result of grafana installation

```bash
helm install grafana \
    --values monitoring/values.yaml \
    --namespace monitoring \
    oci://registry-1.docker.io/bitnamicharts/grafana
Pulled: registry-1.docker.io/bitnamicharts/grafana:11.6.7
Digest: sha256:cea0a30eb1c010ea7226fdff9505e23b7e8a0000e4e2ed89457b5acca023596a
NAME: grafana
LAST DEPLOYED: Wed May  7 21:00:27 2025
NAMESPACE: monitoring
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: grafana
CHART VERSION: 11.6.7
APP VERSION: 11.6.1

Did you know there are enterprise versions of the Bitnami catalog? For enhanced secure software supply chain features, unlimited pulls from Docker, LTS support, or application customization, see Bitnami Premium or Tanzu Application Catalog. See https://www.arrow.com/globalecs/na/vendors/bitnami for more information.

** Please be patient while the chart is being deployed **

1. Get the application URL by running these commands:
    echo "Browse to http://127.0.0.1:8080"
    kubectl port-forward svc/grafana 8080:3000 &

2. Get the admin credentials:

    echo "User: admin"
    echo "Password: $(kubectl get secret grafana-admin --namespace monitoring -o jsonpath="{.data.GF_SECURITY_ADMIN_PASSWORD}" | base64 -d)"
# Note: Do not include grafana.validateValues.database here. See https://github.com/bitnami/charts/issues/20629


WARNING: There are "resources" sections in the chart not set. Using "resourcesPreset" is not recommended for production. For production installations, please set the following values according to your workload needs:
  - grafana.resources
+info https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/


User: admin
Password: rwaP2oDIAj

```

## Installation

Check installation

```bash
helm ls -A
NAME      	NAMESPACE 	REVISION	UPDATED                                 	STATUS  	CHART           	APP VERSION
grafana   	monitoring	1       	2025-05-07 21:00:27.822570129 +0200 CEST	deployed	grafana-11.6.7  	11.6.1     
prometheus	monitoring	1       	2025-05-07 20:46:33.427050531 +0200 CEST	deployed	prometheus-2.0.3	3.3.1    
```

Expose the grafanaservice on http://127.0.0.1:8080/
- User: admin
- Password: rwaP2oDIAj

```bash
kubectl port-forward svc/grafana -n monitoring 8080:3000 &
```