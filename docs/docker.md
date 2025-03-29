# Docker

## Add docker-credential-pass

```bash
sudo apt update && sudo apt install -y pass gnupg

gpg --full-generate-key
# Choose RSA and RSA (default).
# Set key size to 4096 bits.
# Set expiration (or choose never to expire).
# Enter your name and email.

gpg --list-keys
# Copy the key ID (something like ABC123DEF456GHI789).

pass init "YOUR_KEY_ID"

curl -LO https://github.com/docker/docker-credential-helpers/releases/download/v0.9.3/docker-credential-pass-v0.9.3.linux-amd64
chmod +x docker-credential-pass-v0.9.3.linux-amd64
sudo mv docker-credential-pass-v0.9.3.linux-amd64 /usr/local/bin/docker-credential-pass
docker-credential-pass --version
vi ~/.docker/config.json 
# so it contains
#{
#	"auths": {
#	},
#	"credsStore": "pass"
#}

docker login

# check ~/.docker/config.json
cat ~/.docker/config.json 
#{
#	"auths": {
#		"https://index.docker.io/v1/": {},
#		"https://index.docker.io/v1/access-token": {},
#		"https://index.docker.io/v1/refresh-token": {}
#	},
#	"credsStore": "pass"
#}
```