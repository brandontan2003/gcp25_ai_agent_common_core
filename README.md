# GCP25 AI Agent Common Core

> Core library for shared functionality across Google Cloud Platform AI Agent microservices.

This module centralizes common logic, configurations, and utilities used by the AI Agent system—supporting services like the Case Management Service and the AI Agent itself.

---

## 🌐 Google Cloud Platform AI Agent 2025 – Repository Overview
| Repository Name         | GitHub Link                                                                                             | Server Port | DB Port |
|-------------------------|---------------------------------------------------------------------------------------------------------|-------------|---------|
| Case Management Service | [🔗 Case Management Service](https://github.com/brandontan2003/gcp25_ai_agent_case_management_service)  | `8081`      | `3307`  |
| AI Agent Common Core    | [🔗 AI Agent Common Core](https://github.com/brandontan2003/gcp25_ai_agent_common_core)                 | `N/A`       | `N/A`   |
| AI Agent                | [🔗 AI Agent](https://github.com/brandontan2003/gcp25_ai_agent_hackathon)                               | `8082`      | `N/A`   |

## 🧩 Purpose

This repository includes:

- Shared utilities (e.g. logging, exception handling, validation)
- Common DTOs/models across microservices
- Centralized configuration (e.g. GCP connectors, authentication helpers)
- AI-related components: request routing, prompt templates, response parsing

It ensures consistency and reduces duplication across services.

---

## 📦 Getting Started

### Prerequisites

- Java 11+
- Gradle 6.x+
- Access to Google Cloud with the required credentials

### 🛠️ Installation

```bash
git clone https://github.com/brandontan2003/gcp25_ai_agent_common_core.git
cd gcp25_ai_agent_common_core
./gradlew clean build publishToMavenLocal
```
