# 🛒 Spring Boot Product API – DevOps Deployment (Task 2)

This microservice manages a **product catalog** and is deployed using **Docker**, **Kubernetes**, and **GitHub Actions CI/CD**. It supports multiple versions for scalable and isolated deployment.

---

## 🚀 Features

- REST API with `/health`, `/products`, and `/products/search` endpoints
- Versioned releases: `v1.0.0`, `v1.1.0`, `v2.0.0`
- Containerized using Docker with multi-stage builds
- Scalable deployment using Kubernetes + Minikube
- Ingress routing via NGINX using `product.local`
- CI/CD automation using GitHub Actions
- Horizontal Pod Autoscaler for traffic handling

---

## 🧱 Project Structure

```
spring-boot-crud-example/
├── k8s-manifests/
│   ├── deployment-v1.yaml
│   ├── deployment-v1-1.yaml
│   ├── deployment-v2.yaml
│   ├── service-v1.yaml
│   ├── service-v1-1.yaml
│   ├── service-v2.yaml
│   ├── hpa-v1.yaml
│   ├── hpa-v1-1.yaml
│   ├── hpa-v2.yaml
│   ├── namespace-v1.yaml
│   ├── namespace-v1-1.yaml
│   ├── namespace-v2.yaml
│   └── ingress.yaml
├── .github/
│   └── workflows/
│       └── deploy.yml
├── Dockerfile
├── README.md
├── SYSTEM_DESIGN.md
├── CHANGELOG.md
└── ...
```

---

## 🐳 Local Deployment (Docker)

```bash
docker build -t springboot-product-api .
docker run -p 9191:9191 springboot-product-api
```

Visit: [http://localhost:9191/health](http://localhost:9191/health)

---

## ☸️ Kubernetes Setup

### Prerequisites

- Minikube running: `minikube start`
- Enable ingress: `minikube addons enable ingress`
- Add host entry:  
  Edit `C:\Windows\System32\drivers\etc\hosts` and add:  
  ```
  192.168.49.2 product.local
  ```

### Deploy to Kubernetes

```bash
kubectl apply -f k8s-manifests/
kubectl get pods -A
```

---

## 🌐 Ingress Routing

| Version | URL |
|--------|-----|
| v1.0   | http://product.local/v1/health |
| v1.1   | http://product.local/v1-1/health |
| v2.0   | http://product.local/v2/health |

Use this for testing:

```bash
kubectl port-forward -n ingress-nginx service/ingress-nginx-controller 8888:80
```

Then access via: `http://localhost:8888/v1/health`

---

## ⚙️ CI/CD Pipeline (GitHub Actions)

Pipeline triggers on every push to `main`. It performs:

- Docker image build & push to DockerHub
- Applies manifests to Kubernetes cluster
- Optionally run integration tests

---

## 📈 Monitoring & Logs

```bash
kubectl logs <pod-name> -n <namespace>
```

Metrics-server is enabled for HPA (autoscaling).

---

## 📌 Versions

- **v1.0.0** – `/products`, `/health`
- **v1.1.0** – Added `/products/search`
- **v2.0.0** – Enhanced search with query filters and error handling

---

## ✅ Contributor

- Bhoomika N S 👩‍💻
