# 🧠 System Design – Spring Boot Product API

This document outlines the architecture, tools, and decisions made while building a microservice for product catalogue management with production-grade DevOps practices.

---

## 🔧 Tools & Technologies

| Layer | Tools Used |
|-------|------------|
| Source Control | Git + GitHub |
| CI/CD | GitHub Actions |
| Containerization | Docker (Multi-stage build) |
| Deployment | Kubernetes via Minikube |
| Load Management | Horizontal Pod Autoscaler (HPA) |
| Routing | Ingress-NGINX |
| Monitoring | Metrics-Server |
| Versioning | Git tags, Semantic Versioning |

---

## 🔄 Architecture Overview

Each microservice version is deployed in a **separate namespace**, connected via NGINX Ingress, and exposed using a common domain with path-based routing:

```
User ─▶ NGINX Ingress ─▶ Namespace (v1 / v1.1 / v2) ─▶ Service ─▶ Pods
```

---

## 🛠️ Design Decisions

- **Multi-version support**: Allows multiple app versions to run in parallel for A/B testing or phased rollouts.
- **Namespaces**: Provide clear isolation between service versions.
- **Ingress**: Easier external access via path-based routing (`/v1`, `/v1-1`, `/v2`).
- **CI/CD**: Full automation pipeline ensures reliability and repeatability.
- **HPA**: Autoscaling based on CPU ensures high availability under load.
- **Base Image**: Slim Java image used to reduce Docker size.

---

## 🧪 Testing Strategy

- `/health` endpoint used for readiness/liveness.
- Can add test steps to GitHub Actions like:
```bash
curl http://product.local/v1/health
```

---

## 🔒 Future Improvements (Optional)

- TLS termination via Ingress
- Add Prometheus + Grafana for observability
- RBAC for Kubernetes role restrictions
- Use Terraform for provisioning infrastructure

---

## ✅ Summary

This design supports:
- Scalable deployments
- Easy versioning
- Clean separation
- CI/CD automation
- Cloud-native best practices
