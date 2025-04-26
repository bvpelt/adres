# Python APP

Create a virtual environment
```bash
python3 -m venv venv

venv/bin/pip --no-cache-dir install -r requirements.txt
Collecting flask>=2.0.0 (from -r requirements.txt (line 1))
  Downloading flask-3.1.0-py3-none-any.whl.metadata (2.7 kB)
Collecting Werkzeug>=3.1 (from flask>=2.0.0->-r requirements.txt (line 1))
  Downloading werkzeug-3.1.3-py3-none-any.whl.metadata (3.7 kB)
Collecting Jinja2>=3.1.2 (from flask>=2.0.0->-r requirements.txt (line 1))
  Downloading jinja2-3.1.6-py3-none-any.whl.metadata (2.9 kB)
Collecting itsdangerous>=2.2 (from flask>=2.0.0->-r requirements.txt (line 1))
  Downloading itsdangerous-2.2.0-py3-none-any.whl.metadata (1.9 kB)
Collecting click>=8.1.3 (from flask>=2.0.0->-r requirements.txt (line 1))
  Downloading click-8.1.8-py3-none-any.whl.metadata (2.3 kB)
Collecting blinker>=1.9 (from flask>=2.0.0->-r requirements.txt (line 1))
  Downloading blinker-1.9.0-py3-none-any.whl.metadata (1.6 kB)
Collecting MarkupSafe>=2.0 (from Jinja2>=3.1.2->flask>=2.0.0->-r requirements.txt (line 1))
  Downloading MarkupSafe-3.0.2-cp312-cp312-manylinux_2_17_x86_64.manylinux2014_x86_64.whl.metadata (4.0 kB)
Downloading flask-3.1.0-py3-none-any.whl (102 kB)
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 103.0/103.0 kB 15.7 MB/s eta 0:00:00
Downloading blinker-1.9.0-py3-none-any.whl (8.5 kB)
Downloading click-8.1.8-py3-none-any.whl (98 kB)
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 98.2/98.2 kB 20.4 MB/s eta 0:00:00
Downloading itsdangerous-2.2.0-py3-none-any.whl (16 kB)
Downloading jinja2-3.1.6-py3-none-any.whl (134 kB)
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 134.9/134.9 kB 18.7 MB/s eta 0:00:00
Downloading werkzeug-3.1.3-py3-none-any.whl (224 kB)
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 224.5/224.5 kB 17.2 MB/s eta 0:00:00
Downloading MarkupSafe-3.0.2-cp312-cp312-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (23 kB)
Installing collected packages: MarkupSafe, itsdangerous, click, blinker, Werkzeug, Jinja2, flask
Successfully installed Jinja2-3.1.6 MarkupSafe-3.0.2 Werkzeug-3.1.3 blinker-1.9.0 click-8.1.8 flask-3.1.0 itsdangerous-2.2.0

venv/bin/python3 main.py 
 * Serving Flask app 'main'
 * Debug mode: off
WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
 * Running on all addresses (0.0.0.0)
 * Running on `http://127.0.0.1:9001`
 * Running on http://192.168.2.34:9001
Press CTRL+C to quit


# check service
curl http://localhost:9001/hello
{"data":"Hello World"}


```

# Building and Testing Docker image

## 1. Build Docker image
```bash
docker build -t python-rest-api .
[+] Building 34.9s (10/10) FINISHED                                                                                                                                      docker:default
 => [internal] load build definition from dockerfile                                                                                                                               0.0s
 => => transferring dockerfile: 158B                                                                                                                                               0.0s
 => [internal] load metadata for docker.io/library/python:3.13.3                                                                                                                   2.4s
 => [auth] library/python:pull token for registry-1.docker.io                                                                                                                      0.0s
 => [internal] load .dockerignore                                                                                                                                                  0.0s
 => => transferring context: 2B                                                                                                                                                    0.0s
 => [1/4] FROM docker.io/library/python:3.13.3@sha256:34dc8eb488136014caf530ec03a3a2403473a92d67a01a26256c365b5b2fc0d4                                                            29.4s
 => => resolve docker.io/library/python:3.13.3@sha256:34dc8eb488136014caf530ec03a3a2403473a92d67a01a26256c365b5b2fc0d4                                                             0.0s
 => => sha256:f285e872b4525229679829f02db72786ab1a5022d2ab55d7d67ac3cc0becd790 6.26kB / 6.26kB                                                                                     0.0s
 => => sha256:23b7d26ef1d294256da0d70ce374277b9aab5ca683015073316005cb63d33849 48.49MB / 48.49MB                                                                                   8.2s
 => => sha256:07d1b5af933d2dfc3d0dd509d6e20534825e4a537f7b006a6cb5b8e5a1f20905 24.01MB / 24.01MB                                                                                   5.1s
 => => sha256:1eb98adba0eb44a2e4facf9ca3626a4a66feedd0dd56d159cca90a35205744e7 64.40MB / 64.40MB                                                                                  14.0s
 => => sha256:416855cb95a0dd2b7149c91f4af88549def1af9c6c4fa9ab78404578bc97271d 2.32kB / 2.32kB                                                                                     0.0s
 => => sha256:34dc8eb488136014caf530ec03a3a2403473a92d67a01a26256c365b5b2fc0d4 10.04kB / 10.04kB                                                                                   0.0s
 => => sha256:b617a119f8a27982374d94ec6eb3738ae3d38d6fc2c34c865813926cf596a621 211.33MB / 211.33MB                                                                                26.3s
 => => extracting sha256:23b7d26ef1d294256da0d70ce374277b9aab5ca683015073316005cb63d33849                                                                                          0.8s
 => => sha256:171e1bee194953b20a247468c6e03a4d1f67ba21a8034398f4ce3e620880f3ef 6.16MB / 6.16MB                                                                                    10.6s
 => => extracting sha256:07d1b5af933d2dfc3d0dd509d6e20534825e4a537f7b006a6cb5b8e5a1f20905                                                                                          0.2s
 => => sha256:e25cca11fd29c306a338d2d0397b465061f7a0c901916cc9ce7ecee3ed1e4823 27.37MB / 27.37MB                                                                                  18.1s
 => => extracting sha256:1eb98adba0eb44a2e4facf9ca3626a4a66feedd0dd56d159cca90a35205744e7                                                                                          0.9s
 => => sha256:739b86d2a77825bc516695d8b66c3db5b776f53a426387a2ff7acf8567d07049 251B / 251B                                                                                        14.3s
 => => extracting sha256:b617a119f8a27982374d94ec6eb3738ae3d38d6fc2c34c865813926cf596a621                                                                                          2.4s
 => => extracting sha256:171e1bee194953b20a247468c6e03a4d1f67ba21a8034398f4ce3e620880f3ef                                                                                          0.1s
 => => extracting sha256:e25cca11fd29c306a338d2d0397b465061f7a0c901916cc9ce7ecee3ed1e4823                                                                                          0.3s
 => => extracting sha256:739b86d2a77825bc516695d8b66c3db5b776f53a426387a2ff7acf8567d07049                                                                                          0.0s
 => [internal] load build context                                                                                                                                                  0.1s
 => => transferring context: 17.96MB                                                                                                                                               0.1s
 => [2/4] WORKDIR /app                                                                                                                                                             0.6s
 => [3/4] COPY . /app                                                                                                                                                              0.1s
 => [4/4] RUN pip --no-cache-dir install -r requirements.txt                                                                                                                       2.3s
 => exporting to image                                                                                                                                                             0.2s 
 => => exporting layers                                                                                                                                                            0.2s 
 => => writing image sha256:f897d644f1a993ce84312b7490f0c0025ed6c0ba16aed70edb4b8f1143619598                                                                                       0.0s 
 => => naming to docker.io/library/python-rest-api 
 
 # Check docker images
docker image ls
REPOSITORY                    TAG       IMAGE ID       CREATED         SIZE
python-rest-api               latest    f897d644f1a9   5 minutes ago   1.05GB
```

## 2. Run Docker image
```bash
docker run -p 9001:9001 python-rest-api                                                                                      
 * Serving Flask app 'main'                                                                                                                                                             
 * Debug mode: off
WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
 * Running on all addresses (0.0.0.0)
 * Running on http://127.0.0.1:9001
 * Running on http://172.17.0.2:9001
Press CTRL+C to quit

# Check docker image
docker ps
CONTAINER ID   IMAGE             COMMAND             CREATED         STATUS         PORTS                                         NAMES
a97fa7951574   python-rest-api   "python3 main.py"   2 minutes ago   Up 2 minutes   0.0.0.0:9001->9001/tcp, [::]:9001->9001/tcp   wizardly_robinson

# Send get request
curl http://127.0.0.1:9001/hello
{"data":"Hello World"}

```

## 3. Retag docker image

```bash
docker tag python-rest-api dockerpinguin/python-flask-rest-api-project:python-rest-api

docker images
REPOSITORY                                    TAG               IMAGE ID       CREATED          SIZE
dockerpinguin/python-flask-rest-api-project   python-rest-api   f897d644f1a9   14 minutes ago   1.05GB

```

## 4. Push docker image

```bash
docker push dockerpinguin/python-flask-rest-api-project:python-rest-api
The push refers to repository [docker.io/dockerpinguin/python-flask-rest-api-project]
77cf1bdef2de: Pushed 
1b041832256d: Pushed 
d81c37b7f9d8: Pushed 
637e74439bab: Mounted from library/python 
1a6995d3d259: Mounted from library/python 
7036bedc0c75: Mounted from library/python 
6c7c1b88da61: Mounted from library/python 
b2bcbd8ebb2b: Mounted from library/python 
7f0053786e6e: Mounted from library/python 
f7f2b929d8a5: Mounted from library/python 
python-rest-api: digest: sha256:e9bd1163ab2e4db6c606541f682dc20627c077c225b81c1b8aa89e311fa5840c size: 2424

```