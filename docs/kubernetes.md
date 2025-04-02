# Kubernetes

See
- https://www.youtube.com/watch?v=X48VuDVv0do

1:02:00

## Intro
Kubernetes is an open source container orchestration tool.

Developed by Google

Kubernetes helps to manage containerized applications in different envirnoments (physical, virtual, cloud)

Trend monolith -> microservices, need for container management

Kubernetes offers:
- high availability
- scalability
- disaster recovery

Kubernetes components:
- node a physical or virtual machine
- a virtual network
- pod smallest unit of kubernetes
  - an abstraction of a container
  - a layer on top of the container
  - an abstraction of the container
  - usually only 1 container in each pod
  - each pod is efameral, meaning a pod can die at any time. A new one will be created in its place by kubernetes with a new ip adres. 
  - each pod has an ip adress
- service a permanent ip adres connected to 1..n pods
  - the lifecycle of service and pod are not connected
  - a service can be internal or external
  - external service http://128.34.24.1 which is not wat you want
  - load balancer to switch between 1..n available pod's
- ingres expose services to outside world
  - instead of service adres http://128.34.24.1 this would become https://my-app.com
  - ingres forwards to the service
- configmap external configuration of the application
- secret contains critical configuration data such as username/password for a resource.
  - storage of data in base64 encoded format
- volumes
  - attach physical storage to the pod (local or remote to the node where the pod is active)
- deployment
  - blueprint for pod's
  - specify number of pod replica's
  - an abstraction of pod's
  - make scale up/down possible
- statefull set
  - usefull for components which have state (such as a database)
  - database read/writes are synchronized so database inconsistencies do not occur
  - not easy!!!!

### Configuration
Configuration of f.i. a database is usually part of the container image containing the database.
If this is the case, change of required resource means rebuilding the container image, push to repo and pull into pod.
Part of the configuration can also be credentials for a resource (database). These are placed in the secret component.

### Storage
Normally data stored in a pod will be gone when the pod crashes. For a database that is not usefull.
In kubernetes on uses a volume to persist data outside the context of a pod. Think of storage as an external harddrive.
The kubernetes does not manage any persistant data.
Databases can't be replicated by deployment since a database has a state.
Best practice host databases outside the kubernetes cluster.

### Disaster recovery
In order to be resilliant and have no downtime when a pod crashes, the configuration is replicated connected by the same service.
Define replica's in deployments. If one of the replicated nodes crashes, service still continues.

## Kubernetes Architecture
One of the main components of the kubernetes architecture are worker server or nodes. Each node has multiple pod's with containers.
On every node 3 processes must be installed. The are used to schedule and manage the pods. Nodes are the actual components that do the work.
- container runtime
- kubelet to schedule the pods. It interacts with containers and nodes. Kubelet starts the pod with a container inside.
- kubeproxy to forward request to a pod
  - contains logic to optimeze forwarding requests

Interaction with the cluster is done by master processes on a master node which:
- schedule pods
- monitor pods
- reschedule/re-start pods
- join new nodes

Master node has different processes compaired to worker nodes.
- api server, acts as 
  - a cluster gateway. Usages kubernetes dashboard, cli kubectl
  - a gatekeeper for authentication
- scheduler
  - schedules new pod's on one of the worker nodes, has logic to determine which worker node to use. Uses available/desired resources of worker nodes/pod.
- controller manager
  - detect pods/nodes die and take action. Detects state changes, uses scheduler to re-start pods.
- etcd, key value store of the cluster. Cluster brain.
  - resources on each worker node
  - state of pods
  - cluster health
In practice the master node is replicated and load balanced

Master nodes are more important for controlling the kubernetes cluster bu need less resources (cpu, ram, storage) than worker nodes.


## Minikube
Minikube is a local lightweight kubernetes environment with on one node master and worker processes for kubernetes.
- creates virtual box on your machine
- one node runs in the virtual box
- one node contains the kubernetes cluster
- for testing purpose

## Kubectl
A client to interact with the kubernetes cluster using the api server.
Kubectl can interact with each kubernetes cluster.

# Workshop
Install hybervisor (KVM on ubuntu 24.04), minikube and kubectl. See [Actual installation of KVM](#actual-installation-of-kvm)
```bash
minikube start --driver=kvm2

minikube status
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured

kubectl version
Client Version: v1.31.0
Kustomize Version: v5.4.2
Server Version: v1.32.0

```
## Kubectl

**Status of k8s components**
- kubectl get nodes | pod | services | replicaset | deployment

```bash
kubectl get nodes
NAME       STATUS   ROLES           AGE   VERSION
minikube   Ready    control-plane   11m   v1.32.0

kubectl get pod
No resources found in default namespace.

kubectl get services
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   27m
```
**CRUD commands**
- create deployment: kubectl create deployment <name>
- edit deployment:   kubectl edit deployment <name>
- delete deployment: kubectl delete deployment <name>

```bash
# Create pod
# Pod is smallest unit of kubernetes which cannot be created.
# Instead a deployment which describes the pod is created
# usage: kubectl create deployment <name> --image=<imagename> [--dry-run] [options]
kubectl create deployment nginx-depl --image=nginx
deployment.apps/nginx-depl created

# When creating the deployment, the deployment has the blueprint for the pod. 
# The blueprint is the minimalistic configuration for the pod. 
# Only name and image are required. Rest of parameters are defaults

kubectl get deployment
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
nginx-depl   1/1     1            1           40s

kubectl get pod
NAME                          READY   STATUS    RESTARTS   AGE
nginx-depl-68c944fcbc-82vtm   1/1     Running   0          4m16s

# Replicaset is a component between deployment and pod
kubectl get replicaset
NAME                    DESIRED   CURRENT   READY   AGE
nginx-depl-68c944fcbc   1         1         1       10m

kubectl edit deployment nginx-depl
# Opens editor (vi) with autogenerated defaults


# Please edit the object below. Lines beginning with a '#' will be ignored,
# and an empty file will abort the edit. If an error occurs while saving this file will be
# reopened with the relevant failures.
#
`apiVersion: apps/v1
kind: Deployment
metadata:
  annotations:
    deployment.kubernetes.io/revision: "1"
  creationTimestamp: "2025-04-02T06:45:33Z"
  generation: 1
  labels:
    app: nginx-depl
  name: nginx-depl
  namespace: default
  resourceVersion: "1936"
  uid: 81dc9fd6-2219-49ee-80a9-8b1383c610a6
spec:
  progressDeadlineSeconds: 600
  replicas: 1
  revisionHistoryLimit: 10
  selector:
    matchLabels:
      app: nginx-depl
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
    type: RollingUpdate
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: nginx-depl
    spec:
      containers:
      - image: nginx
        imagePullPolicy: Always
        name: nginx
        resources: {}
        terminationMessagePath: /dev/termination-log
        terminationMessagePolicy: File
      dnsPolicy: ClusterFirst
      restartPolicy: Always
      schedulerName: default-scheduler
      securityContext: {}
      terminationGracePeriodSeconds: 30
status:
  availableReplicas: 1
  conditions:
  - lastTransitionTime: "2025-04-02T06:45:47Z"
    lastUpdateTime: "2025-04-02T06:45:47Z"
    message: Deployment has minimum availability.
    reason: MinimumReplicasAvailable
    status: "True"
    type: Available
  - lastTransitionTime: "2025-04-02T06:45:33Z"
    lastUpdateTime: "2025-04-02T06:45:47Z"
    message: ReplicaSet "nginx-depl-68c944fcbc" has successfully progressed.
    reason: NewReplicaSetAvailable
    status: "True"
    type: Progressing
  observedGeneration: 1
  readyReplicas: 1
  replicas: 1
  updatedReplicas: 1`

# After changing the version of the image nginx to nginx:1.26.3
# The old pod (see above for the name) is stopped and replaced by the new pod with a new name.  
kubectl get pod
NAME                          READY   STATUS    RESTARTS   AGE
nginx-depl-5c7559999c-8jqh4   1/1     Running   0          20s
  
```

**Debugging pods**
- log to console:        kubectl logs <pod name>
- get internal terminal: kubectl exec -it <pod name> -- bin/bash

```bash
kubectl get pods
NAME                          READY   STATUS    RESTARTS   AGE
nginx-depl-5c7559999c-8jqh4   1/1     Running   0          5m23s

kubectl logs nginx-depl-5c7559999c-8jqh4
/docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
/docker-entrypoint.sh: Looking for shell scripts in /docker-entrypoint.d/
/docker-entrypoint.sh: Launching /docker-entrypoint.d/10-listen-on-ipv6-by-default.sh
10-listen-on-ipv6-by-default.sh: info: Getting the checksum of /etc/nginx/conf.d/default.conf
10-listen-on-ipv6-by-default.sh: info: Enabled listen on IPv6 in /etc/nginx/conf.d/default.conf
/docker-entrypoint.sh: Sourcing /docker-entrypoint.d/15-local-resolvers.envsh
/docker-entrypoint.sh: Launching /docker-entrypoint.d/20-envsubst-on-templates.sh
/docker-entrypoint.sh: Launching /docker-entrypoint.d/30-tune-worker-processes.sh
/docker-entrypoint.sh: Configuration complete; ready for start up
2025/04/02 18:23:54 [notice] 1#1: using the "epoll" event method
2025/04/02 18:23:54 [notice] 1#1: nginx/1.26.3
2025/04/02 18:23:54 [notice] 1#1: built by gcc 12.2.0 (Debian 12.2.0-14) 
2025/04/02 18:23:54 [notice] 1#1: OS: Linux 5.10.207
2025/04/02 18:23:54 [notice] 1#1: getrlimit(RLIMIT_NOFILE): 1048576:1048576
2025/04/02 18:23:54 [notice] 1#1: start worker processes
2025/04/02 18:23:54 [notice] 1#1: start worker process 29
2025/04/02 18:23:54 [notice] 1#1: start worker process 30

# create pod which will log something
kubectl create deployment mongo-depl --image=mongo
deployment.apps/mongo-depl created


kubectl get pod
NAME                          READY   STATUS              RESTARTS   AGE
mongo-depl-85ffbc9879-fkrlh   0/1     ContainerCreating   0          26s
nginx-depl-5c7559999c-8jqh4   1/1     Running             0          8m18s

# Show logging of the pod
kubectl logs mongo-depl-85ffbc9879-fkrlh
{"t":{"$date":"2025-04-02T18:32:05.936+00:00"},"s":"I",  "c":"CONTROL",  "id":23285,   "ctx":"main","msg":"Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'"}
{"t":{"$date":"2025-04-02T18:32:05.936+00:00"},"s":"I",  "c":"CONTROL",  "id":5945603, "ctx":"main","msg":"Multi threading initialized"}
{"t":{"$date":"2025-04-02T18:32:05.936+00:00"},"s":"I",  "c":"NETWORK",  "id":4648601, "ctx":"main","msg":"Implicit TCP FastOpen unavailable. If TCP FastOpen is required, set at least one of the related parameters","attr":{"relatedParameters":["tcpFastOpenServer","tcpFastOpenClient","tcpFastOpenQueueSize"]}}
{"t":{"$date":"2025-04-02T18:32:05.937+00:00"},"s":"I",  "c":"NETWORK",  "id":4915701, "ctx":"main","msg":"Initialized wire specification","attr":{"spec":{"incomingExternalClient":{"minWireVersion":0,"maxWireVersion":25},"incomingInternalClient":{"minWireVersion":0,"maxWireVersion":25},"outgoing":{"minWireVersion":6,"maxWireVersion":25},"isInternalClient":true}}}

# Describe the content of the pod
kubectl describe pod mongo-depl-85ffbc9879-fkrlh
Name:             mongo-depl-85ffbc9879-fkrlh
Namespace:        default
Priority:         0
Service Account:  default
Node:             minikube/192.168.39.87
Start Time:       Wed, 02 Apr 2025 20:31:39 +0200
Labels:           app=mongo-depl
                  pod-template-hash=85ffbc9879
Annotations:      <none>
Status:           Running
IP:               10.244.0.7
IPs:
  IP:           10.244.0.7
Controlled By:  ReplicaSet/mongo-depl-85ffbc9879
Containers:
  mongo:
    Container ID:   docker://30722d0b72c78415769e0c7309080fa88b3a1717a6b7c0a85ec5c18af71a70ce
    Image:          mongo
    Image ID:       docker-pullable://mongo@sha256:1cb283500219e8fc0b61b328ea5a199a395a753d88b17351c58874fb425223cb
    Port:           <none>
    Host Port:      <none>
    State:          Running
      Started:      Wed, 02 Apr 2025 20:32:05 +0200
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-hwltz (ro)
Conditions:
  Type                        Status
  PodReadyToStartContainers   True 
  Initialized                 True 
  Ready                       True 
  ContainersReady             True 
  PodScheduled                True 
Volumes:
  kube-api-access-hwltz:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age    From               Message
  ----    ------     ----   ----               -------
  Normal  Scheduled  2m38s  default-scheduler  Successfully assigned default/mongo-depl-85ffbc9879-fkrlh to minikube
  Normal  Pulling    2m39s  kubelet            Pulling image "mongo"
  Normal  Pulled     2m13s  kubelet            Successfully pulled image "mongo" in 25.632s (25.632s including waiting). Image size: 887476000 bytes.
  Normal  Created    2m13s  kubelet            Created container: mongo
  Normal  Started    2m12s  kubelet            Started container mongo

# Get terminal in pod  -it = interactive terminal
kubectl exec -it mongo-depl-85ffbc9879-fkrlh -- bin/bash
root@mongo-depl-85ffbc9879-fkrlh:/# 
root@mongo-depl-85ffbc9879-fkrlh:/# ls
bin  boot  data  dev  docker-entrypoint-initdb.d  etc  home  js-yaml.js  lib  lib64  media  mnt  opt  proc  root  run  sbin  srv  sys  tmp  usr  var
root@mongo-depl-85ffbc9879-fkrlh:/# exit
exit

# Get info
kubectl get deployment
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
mongo-depl   1/1     1            1           26m
nginx-depl   1/1     1            1           12h

kubectl get pod
NAME                          READY   STATUS    RESTARTS   AGE
mongo-depl-85ffbc9879-fkrlh   1/1     Running   0          31m
nginx-depl-5c7559999c-8jqh4   1/1     Running   0          39m

# Delete deployment
kubectl delete deployment mongo-depl 
deployment.apps "mongo-depl" deleted

kubectl get deployment
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
nginx-depl   1/1     1            1           12h

kubectl get pod
NAME                          READY   STATUS    RESTARTS   AGE
nginx-depl-5c7559999c-8jqh4   1/1     Running   0          41m

```

## Fine tune configuration
To finetune configuration one uses configuration files with as syntax:

kubectl apply -f <configuration file>

The format of the configuration file is in yaml.

```bash
kubectl apply -f nginx-deployment.yaml 
deployment.apps/nginx-deployment created

kubectl get pods
NAME                                READY   STATUS    RESTARTS   AGE
nginx-deployment-558d6675d6-tw7km   1/1     Running   0          4s

# After changing replicas from 1 -> 2 an extra instance is created.
# Status shows configured instead of created
#
kubectl apply -f nginx-deployment.yaml 
deployment.apps/nginx-deployment configured

kubectl get pods
NAME                                READY   STATUS    RESTARTS   AGE
nginx-deployment-558d6675d6-2tnxq   1/1     Running   0          3s
nginx-deployment-558d6675d6-tw7km   1/1     Running   0          119s

```

### Layers of abstraction
A deployment manages replicasets
A replicaset manages pod
A pod is an abstraction of a container

## Install KVM
check virtualisation
```bash
lscpu | grep Virtualization
Virtualization:                       VT-x
# or
egrep -c '(vmx|svm)' /proc/cpuinfo
28
```

```bash
minikube start --driver=kvm2
kubectl get nodes
minikube status
kubectl version # client/server version of kubernetes
```
## Actual installation of KVM
```bash
sudo apt update
sudo apt install qemu-kvm libvirt-daemon-system libvirt-clients virt-manager bridge-utils
# qemu-kvm: Provides the KVM hypervisor.
# libvirt-daemon-system: Manages virtual machines.
# libvirt-clients: Provides tools for managing virtual machines.
# virt-manager: A graphical user interface for managing virtual machines.
# bridge-utils: Tools for configuring network bridges.

# Add user to groups kvm, libvirt
sudo adduser $USER kvm
sudo adduser $USER libvirt

# get group membership
# logout/login

# verify installation
virsh list --all
```


  



