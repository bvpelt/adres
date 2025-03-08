# Minikube (Tutorial)

Using minikube 

```bash
# start minikube - after installing minikube and kubectl
minikube start

# opening dashboard - using another terminal session, opens dashboard in the browser!!!
minikube dashboard

# Only get the url for the dashboard
# Start a new terminal, and leave this running.
minikube dashboard --url
```

## Create deployment

```bash
# Run a test container image that includes a webserver
$ kubectl create deployment hello-node --image=registry.k8s.io/e2e-test-images/agnhost:2.39 -- /agnhost netexec --http-port=8080

# View deployment
$ kubectl get deployments
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
hello-node   1/1     1            1           59s

# View the pod
$ kubectl get pods
NAME                         READY   STATUS    RESTARTS   AGE
hello-node-c74958b5d-4lcz5   1/1     Running   0          113s

# View cluster events
$ kubectl get events
LAST SEEN   TYPE      REASON                    OBJECT                            MESSAGE
2m33s       Normal    Scheduled                 pod/hello-node-c74958b5d-4lcz5    Successfully assigned default/hello-node-c74958b5d-4lcz5 to minikube
2m32s       Normal    Pulling                   pod/hello-node-c74958b5d-4lcz5    Pulling image "registry.k8s.io/e2e-test-images/agnhost:2.39"
2m24s       Normal    Pulled                    pod/hello-node-c74958b5d-4lcz5    Successfully pulled image "registry.k8s.io/e2e-test-images/agnhost:2.39" in 8.507s (8.507s including waiting). Image size: 126872991 bytes.
2m22s       Normal    Created                   pod/hello-node-c74958b5d-4lcz5    Created container: agnhost
2m21s       Normal    Started                   pod/hello-node-c74958b5d-4lcz5    Started container agnhost
2m34s       Normal    SuccessfulCreate          replicaset/hello-node-c74958b5d   Created pod: hello-node-c74958b5d-4lcz5
2m34s       Normal    ScalingReplicaSet         deployment/hello-node             Scaled up replica set hello-node-c74958b5d from 0 to 1
22h         Normal    Starting                  node/minikube                     Starting kubelet.
22h         Normal    NodeAllocatableEnforced   node/minikube                     Updated Node Allocatable limit across pods
22h         Normal    NodeHasSufficientMemory   node/minikube                     Node minikube status is now: NodeHasSufficientMemory
22h         Normal    NodeHasNoDiskPressure     node/minikube                     Node minikube status is now: NodeHasNoDiskPressure
22h         Normal    NodeHasSufficientPID      node/minikube                     Node minikube status is now: NodeHasSufficientPID
22h         Normal    NodeReady                 node/minikube                     Node minikube status is now: NodeReady
22h         Normal    RegisteredNode            node/minikube                     Node minikube event: Registered Node minikube in Controller
22h         Normal    Starting                  node/minikube                     
11m         Normal    Starting                  node/minikube                     Starting kubelet.
11m         Normal    NodeHasSufficientMemory   node/minikube                     Node minikube status is now: NodeHasSufficientMemory
11m         Normal    NodeHasNoDiskPressure     node/minikube                     Node minikube status is now: NodeHasNoDiskPressure
11m         Normal    NodeHasSufficientPID      node/minikube                     Node minikube status is now: NodeHasSufficientPID
11m         Normal    NodeAllocatableEnforced   node/minikube                     Updated Node Allocatable limit across pods
11m         Warning   Rebooted                  node/minikube                     Node minikube has been rebooted, boot id: 2a9a3704-b7e1-4bc4-ab7c-35cbc56c6f64
11m         Normal    RegisteredNode            node/minikube                     Node minikube event: Registered Node minikube in Controller
10m         Normal    Starting                  node/minikube                     

# View kubectl configuration
$ kubectl config view
apiVersion: v1
clusters:
- cluster:
    certificate-authority: /home/bvpelt/.minikube/ca.crt
    extensions:
    - extension:
        last-update: Sat, 08 Mar 2025 18:51:01 CET
        provider: minikube.sigs.k8s.io
        version: v1.35.0
      name: cluster_info
    server: https://192.168.49.2:8443
  name: minikube
contexts:
- context:
    cluster: minikube
    extensions:
    - extension:
        last-update: Sat, 08 Mar 2025 18:51:01 CET
        provider: minikube.sigs.k8s.io
        version: v1.35.0
      name: context_info
    namespace: default
    user: minikube
  name: minikube
current-context: minikube
kind: Config
preferences: {}
users:
- name: minikube
  user:
    client-certificate: /home/bvpelt/.minikube/profiles/minikube/client.crt
    client-key: /home/bvpelt/.minikube/profiles/minikube/client.key

# View application logs for a container in a pod
$ kubectl logs hello-node-c74958b5d-4lcz5 
I0308 18:00:38.996508       1 log.go:195] Started HTTP server on port 8080
I0308 18:00:38.996747       1 log.go:195] Started UDP server on port  8081
```

### Create a service

```bash
# expose the pod to the internet
$ kubectl expose deployment hello-node --type=LoadBalancer --port=8080
service/hello-node exposed

# The --type=LoadBalancer flag indicates that you want to expose your Service outside of the cluster.


# View services
$ kubectl get services
NAME         TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
hello-node   LoadBalancer   10.104.31.89   <pending>     8080:32490/TCP   65s
kubernetes   ClusterIP      10.96.0.1      <none>        443/TCP          22h

# On cloud providers that support load balancers, an external IP address would be provisioned to access the Service. On minikube, the LoadBalancer type makes the Service accessible through the minikube service command.


$ minikube service hello-node
|-----------|------------|-------------|---------------------------|
| NAMESPACE |    NAME    | TARGET PORT |            URL            |
|-----------|------------|-------------|---------------------------|
| default   | hello-node |        8080 | http://192.168.49.2:32490 |
|-----------|------------|-------------|---------------------------|
🎉  Opening service default/hello-node in default browser...
bvpelt@pluto:~/Develop/adres$ Opening in existing browser session.

# This opens up a browser window that serves your app and shows the app's response.

```

### Addons

```bash
# List supported addons
$ minikube addons list
|-----------------------------|----------|--------------|--------------------------------|
|         ADDON NAME          | PROFILE  |    STATUS    |           MAINTAINER           |
|-----------------------------|----------|--------------|--------------------------------|
| ambassador                  | minikube | disabled     | 3rd party (Ambassador)         |
| amd-gpu-device-plugin       | minikube | disabled     | 3rd party (AMD)                |
| auto-pause                  | minikube | disabled     | minikube                       |
| cloud-spanner               | minikube | disabled     | Google                         |
| csi-hostpath-driver         | minikube | disabled     | Kubernetes                     |
| dashboard                   | minikube | enabled ✅   | Kubernetes                     |
| default-storageclass        | minikube | enabled ✅   | Kubernetes                     |
| efk                         | minikube | disabled     | 3rd party (Elastic)            |
| freshpod                    | minikube | disabled     | Google                         |
| gcp-auth                    | minikube | disabled     | Google                         |
| gvisor                      | minikube | disabled     | minikube                       |
| headlamp                    | minikube | disabled     | 3rd party (kinvolk.io)         |
| inaccel                     | minikube | disabled     | 3rd party (InAccel             |
|                             |          |              | [info@inaccel.com])            |
| ingress                     | minikube | disabled     | Kubernetes                     |
| ingress-dns                 | minikube | disabled     | minikube                       |
| inspektor-gadget            | minikube | disabled     | 3rd party                      |
|                             |          |              | (inspektor-gadget.io)          |
| istio                       | minikube | disabled     | 3rd party (Istio)              |
| istio-provisioner           | minikube | disabled     | 3rd party (Istio)              |
| kong                        | minikube | disabled     | 3rd party (Kong HQ)            |
| kubeflow                    | minikube | disabled     | 3rd party                      |
| kubevirt                    | minikube | disabled     | 3rd party (KubeVirt)           |
| logviewer                   | minikube | disabled     | 3rd party (unknown)            |
| metallb                     | minikube | disabled     | 3rd party (MetalLB)            |
| metrics-server              | minikube | enabled ✅   | Kubernetes                     |
| nvidia-device-plugin        | minikube | disabled     | 3rd party (NVIDIA)             |
| nvidia-driver-installer     | minikube | disabled     | 3rd party (NVIDIA)             |
| nvidia-gpu-device-plugin    | minikube | disabled     | 3rd party (NVIDIA)             |
| olm                         | minikube | disabled     | 3rd party (Operator Framework) |
| pod-security-policy         | minikube | disabled     | 3rd party (unknown)            |
| portainer                   | minikube | disabled     | 3rd party (Portainer.io)       |
| registry                    | minikube | disabled     | minikube                       |
| registry-aliases            | minikube | disabled     | 3rd party (unknown)            |
| registry-creds              | minikube | disabled     | 3rd party (UPMC Enterprises)   |
| storage-provisioner         | minikube | enabled ✅   | minikube                       |
| storage-provisioner-gluster | minikube | disabled     | 3rd party (Gluster)            |
| storage-provisioner-rancher | minikube | disabled     | 3rd party (Rancher)            |
| volcano                     | minikube | disabled     | third-party (volcano)          |
| volumesnapshots             | minikube | disabled     | Kubernetes                     |
| yakd                        | minikube | disabled     | 3rd party (marcnuri.com)       |
|-----------------------------|----------|--------------|--------------------------------|

# Enable metrics-server
$ minikube addons enable metrics-server
💡  metrics-server is an addon maintained by Kubernetes. For any concerns contact minikube on GitHub.
You can view the list of minikube maintainers at: https://github.com/kubernetes/minikube/blob/master/OWNERS
    ▪ Using image registry.k8s.io/metrics-server/metrics-server:v0.7.2
🌟  The 'metrics-server' addon is enabled

# View pod and service created by installing the addon
$ kubectl get pod,svc -n kube-system
NAME                                   READY   STATUS    RESTARTS      AGE
pod/coredns-668d6bf9bc-n6wtd           1/1     Running   1 (22h ago)   22h
pod/etcd-minikube                      1/1     Running   1 (22h ago)   22h
pod/kube-apiserver-minikube            1/1     Running   1 (22h ago)   22h
pod/kube-controller-manager-minikube   1/1     Running   1 (22h ago)   22h
pod/kube-proxy-kqzfp                   1/1     Running   1 (22h ago)   22h
pod/kube-scheduler-minikube            1/1     Running   1 (22h ago)   22h
pod/metrics-server-7fbb699795-tmqcw    1/1     Running   2 (25m ago)   22h
pod/storage-provisioner                1/1     Running   3 (25m ago)   22h

NAME                     TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                  AGE
service/kube-dns         ClusterIP   10.96.0.10       <none>        53/UDP,53/TCP,9153/TCP   22h
service/metrics-server   ClusterIP   10.103.186.101   <none>        443/TCP                  22h

# Check output from metrics-server
$ kubectl top pods
NAME                         CPU(cores)   MEMORY(bytes)   
hello-node-c74958b5d-4lcz5   1m           6Mi  
```

## Cleanup

```bash
# cleanup resources previously created
$ kubectl delete service hello-node
service "hello-node" deleted

$ kubectl delete deployment hello-node
deployment.apps "hello-node" deleted
```

