# Database

Use postgresql from https://github.com/bitnami/charts/tree/main/bitnami/postgresql

Check using template from the adres/helm directory

```bash
helm template oci://registry-1.docker.io/bitnamicharts/postgresql -f database/values.yaml 
Pulled: registry-1.docker.io/bitnamicharts/postgresql:16.7.1
Digest: sha256:bbbc0f0197b8e0cad8b305a34b9c53e29c3ff049a1adbfd09702f795d63d22d7
---
# Source: postgresql/templates/primary/networkpolicy.yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: release-name-postgresql
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
    app.kubernetes.io/component: primary
spec:
  podSelector:
    matchLabels:
      app.kubernetes.io/instance: release-name
      app.kubernetes.io/name: postgresql
      app.kubernetes.io/component: primary
  policyTypes:
    - Ingress
    - Egress
  egress:
    - {}
  ingress:
    - ports:
        - port: 5432
        - port: 9187
---
# Source: postgresql/templates/primary/pdb.yaml
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: release-name-postgresql
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
    app.kubernetes.io/component: primary
spec:
  maxUnavailable: 1
  selector:
    matchLabels:
      app.kubernetes.io/instance: release-name
      app.kubernetes.io/name: postgresql
      app.kubernetes.io/component: primary
---
# Source: postgresql/templates/serviceaccount.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: release-name-postgresql
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
automountServiceAccountToken: false
---
# Source: postgresql/templates/secrets.yaml
apiVersion: v1
kind: Secret
metadata:
  name: release-name-postgresql
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
type: Opaque
data:
  postgres-password: "cG9zdGdyZXM="
  password: "MTIzNDU="
  # We don't auto-generate LDAP password when it's not provided as we do for other passwords
---
# Source: postgresql/templates/primary/metrics-svc.yaml
apiVersion: v1
kind: Service
metadata:
  name: release-name-postgresql-metrics
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
    app.kubernetes.io/component: metrics
  annotations:
    prometheus.io/port: "9187"
    prometheus.io/scrape: "true"
spec:
  type: ClusterIP
  sessionAffinity: None
  ports:
    - name: http-metrics
      port: 9187
      targetPort: http-metrics
  selector:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/component: primary
---
# Source: postgresql/templates/primary/svc-headless.yaml
apiVersion: v1
kind: Service
metadata:
  name: release-name-postgresql-hl
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
    app.kubernetes.io/component: primary
  annotations:
spec:
  type: ClusterIP
  clusterIP: None
  # We want all pods in the StatefulSet to have their addresses published for
  # the sake of the other Postgresql pods even before they're ready, since they
  # have to be able to talk to each other in order to become ready.
  publishNotReadyAddresses: true
  ports:
    - name: tcp-postgresql
      port: 5432
      targetPort: tcp-postgresql
  selector:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/component: primary
---
# Source: postgresql/templates/primary/svc.yaml
apiVersion: v1
kind: Service
metadata:
  name: release-name-postgresql
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
    app.kubernetes.io/component: primary
spec:
  type: ClusterIP
  sessionAffinity: None
  ports:
    - name: tcp-postgresql
      port: 5432
      targetPort: tcp-postgresql
      nodePort: null
  selector:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/component: primary
---
# Source: postgresql/templates/primary/statefulset.yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: release-name-postgresql
  namespace: "default"
  labels:
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: postgresql
    app.kubernetes.io/version: 17.5.0
    helm.sh/chart: postgresql-16.7.1
    app.kubernetes.io/component: primary
spec:
  replicas: 1
  serviceName: release-name-postgresql-hl
  updateStrategy:
    rollingUpdate: {}
    type: RollingUpdate
  selector:
    matchLabels:
      app.kubernetes.io/instance: release-name
      app.kubernetes.io/name: postgresql
      app.kubernetes.io/component: primary
  template:
    metadata:
      name: release-name-postgresql
      labels:
        app.kubernetes.io/instance: release-name
        app.kubernetes.io/managed-by: Helm
        app.kubernetes.io/name: postgresql
        app.kubernetes.io/version: 17.5.0
        helm.sh/chart: postgresql-16.7.1
        app.kubernetes.io/component: primary
    spec:
      serviceAccountName: release-name-postgresql
      
      automountServiceAccountToken: false
      affinity:
        podAffinity:
          
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - podAffinityTerm:
                labelSelector:
                  matchLabels:
                    app.kubernetes.io/instance: release-name
                    app.kubernetes.io/name: postgresql
                    app.kubernetes.io/component: primary
                topologyKey: kubernetes.io/hostname
              weight: 1
        nodeAffinity:
          
      securityContext:
        fsGroup: 1001
        fsGroupChangePolicy: Always
        supplementalGroups: []
        sysctls: []
      hostNetwork: false
      hostIPC: false
      containers:
        - name: postgresql
          image: docker.io/bitnami/postgresql:17.5.0-debian-12-r0
          imagePullPolicy: "IfNotPresent"
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
          env:
            - name: BITNAMI_DEBUG
              value: "false"
            - name: POSTGRESQL_PORT_NUMBER
              value: "5432"
            - name: POSTGRESQL_VOLUME_DIR
              value: "/bitnami/postgresql"
            - name: PGDATA
              value: "/bitnami/postgresql/data"
            # Authentication
            - name: POSTGRES_USER
              value: "testuser"
            - name: POSTGRES_PASSWORD_FILE
              value: /opt/bitnami/postgresql/secrets/password
            - name: POSTGRES_POSTGRES_PASSWORD_FILE
              value: /opt/bitnami/postgresql/secrets/postgres-password
            - name: POSTGRES_DATABASE
              value: "adres"
            # LDAP
            - name: POSTGRESQL_ENABLE_LDAP
              value: "no"
            # TLS
            - name: POSTGRESQL_ENABLE_TLS
              value: "no"
            # Audit
            - name: POSTGRESQL_LOG_HOSTNAME
              value: "false"
            - name: POSTGRESQL_LOG_CONNECTIONS
              value: "false"
            - name: POSTGRESQL_LOG_DISCONNECTIONS
              value: "false"
            - name: POSTGRESQL_PGAUDIT_LOG_CATALOG
              value: "off"
            # Others
            - name: POSTGRESQL_CLIENT_MIN_MESSAGES
              value: "error"
            - name: POSTGRESQL_SHARED_PRELOAD_LIBRARIES
              value: "pgaudit"
          ports:
            - name: tcp-postgresql
              containerPort: 5432
          livenessProbe:
            failureThreshold: 6
            initialDelaySeconds: 30
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 5
            exec:
              command:
                - /bin/sh
                - -c
                - exec pg_isready -U "testuser" -d "dbname=adres" -h 127.0.0.1 -p 5432
          readinessProbe:
            failureThreshold: 6
            initialDelaySeconds: 5
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 5
            exec:
              command:
                - /bin/sh
                - -c
                - -e
                - |
                  exec pg_isready -U "testuser" -d "dbname=adres" -h 127.0.0.1 -p 5432
                  [ -f /opt/bitnami/postgresql/tmp/.initialized ] || [ -f /bitnami/postgresql/.initialized ]
          resources:
            limits:
              cpu: 150m
              ephemeral-storage: 2Gi
              memory: 192Mi
            requests:
              cpu: 100m
              ephemeral-storage: 50Mi
              memory: 128Mi
          volumeMounts:
            - name: empty-dir
              mountPath: /tmp
              subPath: tmp-dir
            - name: empty-dir
              mountPath: /opt/bitnami/postgresql/conf
              subPath: app-conf-dir
            - name: empty-dir
              mountPath: /opt/bitnami/postgresql/tmp
              subPath: app-tmp-dir
            - name: postgresql-password
              mountPath: /opt/bitnami/postgresql/secrets/
            - name: dshm
              mountPath: /dev/shm
            - name: data
              mountPath: /bitnami/postgresql
        - name: metrics
          image: docker.io/bitnami/postgres-exporter:0.17.1-debian-12-r7
          imagePullPolicy: "IfNotPresent"
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
          env:
            - name: DATA_SOURCE_URI
              value: 127.0.0.1:5432/postgres?sslmode=disable
            - name: DATA_SOURCE_PASS_FILE
              value: /opt/bitnami/postgresql/secrets/password
            - name: DATA_SOURCE_USER
              value: "testuser"
          ports:
            - name: http-metrics
              containerPort: 9187
          livenessProbe:
            failureThreshold: 6
            initialDelaySeconds: 5
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 5
            httpGet:
              path: /
              port: http-metrics
          readinessProbe:
            failureThreshold: 6
            initialDelaySeconds: 5
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 5
            httpGet:
              path: /
              port: http-metrics
          volumeMounts:
            - name: empty-dir
              mountPath: /tmp
              subPath: tmp-dir
            - name: postgresql-password
              mountPath: /opt/bitnami/postgresql/secrets/
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
        - name: postgresql-password
          secret:
            secretName: release-name-postgresql
        - name: dshm
          emptyDir:
            medium: Memory
  volumeClaimTemplates:
    - apiVersion: v1
      kind: PersistentVolumeClaim
      metadata:
        name: data
      spec:
        accessModes:
          - "ReadWriteOnce"
        resources:
          requests:
            storage: "8Gi"
bvpelt@uranus:~/Develop/adres/helm$ 


```
```bash
helm install postgres oci://registry-1.docker.io/bitnamicharts/postgresql -f database/values.yaml 
Pulled: registry-1.docker.io/bitnamicharts/postgresql:16.7.1
Digest: sha256:bbbc0f0197b8e0cad8b305a34b9c53e29c3ff049a1adbfd09702f795d63d22d7
NAME: postgres
LAST DEPLOYED: Fri May  9 21:03:51 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: postgresql
CHART VERSION: 16.7.1
APP VERSION: 17.5.0

Did you know there are enterprise versions of the Bitnami catalog? For enhanced secure software supply chain features, unlimited pulls from Docker, LTS support, or application customization, see Bitnami Premium or Tanzu Application Catalog. See https://www.arrow.com/globalecs/na/vendors/bitnami for more information.

** Please be patient while the chart is being deployed **

PostgreSQL can be accessed via port 5432 on the following DNS names from within your cluster:

    postgres-postgresql.default.svc.cluster.local - Read/Write connection

To get the password for "postgres" run:

    export POSTGRES_ADMIN_PASSWORD=$(kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d)

To get the password for "testuser" run:

    export POSTGRES_PASSWORD=$(kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.password}" | base64 -d)

To connect to your database run the following command:

    kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:17.5.0-debian-12-r0 --env="PGPASSWORD=$POSTGRES_PASSWORD" \
      --command -- psql --host postgres-postgresql -U testuser -d adres -p 5432

    > NOTE: If you access the container using bash, make sure that you execute "/opt/bitnami/scripts/postgresql/entrypoint.sh /bin/bash" in order to avoid the error "psql: local user with ID 1001} does not exist"

To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace default svc/postgres-postgresql 5432:5432 &
    PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U testuser -d adres -p 5432

WARNING: The configured password will be ignored on new installation in case when previous PostgreSQL release was deleted through the helm command. In that case, old PVC will have an old password, and setting it through helm won't take effect. Deleting persistent volumes (PVs) will solve the issue.

WARNING: There are "resources" sections in the chart not set. Using "resourcesPreset" is not recommended for production. For production installations, please set the following values according to your workload needs:
  - metrics.resources
  - primary.resources
  - readReplicas.resources
+info https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/

# Admin password for postgres
kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d
postgres

# Password for user testuser
kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.password}" | base64 -dase64 -d
12345

kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:17.5.0-debian-12-r0 --env="PGPASSWORD=12345" \
      --command -- psql --host postgres-postgresql -U testuser -d adres -p 5432
      
helm ls
NAME    	NAMESPACE	REVISION	UPDATED                                 	STATUS  	CHART            	APP VERSION
postgres	default  	1       	2025-05-09 21:03:51.716796973 +0200 CEST	deployed	postgresql-16.7.1	17.5.0           
```

In grafana create postgres data connection which uses postgres-postgresql.default.svc.cluster.local and has tld disabled

Use dashboard https://grafana.com/grafana/dashboards/9628-postgresql-database/

