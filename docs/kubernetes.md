# Kubernetes

See
- https://www.youtube.com/watch?v=X48VuDVv0do

44:50

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
Install hybervisor (KVM on ubuntu 24.04), minikube and kubectl. See [Actual installation of KVM](#Actual installation of KVM)

minikube start --driver=kvm2

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


  



