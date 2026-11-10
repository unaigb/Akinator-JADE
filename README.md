<div align="center">

# 🕵️ Akinator con agentes JADE

**Sistema multiagente que juega a «¿Quién es quién?» recorriendo un árbol de decisión J48 de Weka**

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)]()
[![JADE](https://img.shields.io/badge/JADE-multiagente-1F7A8C?style=flat)]()
[![Weka](https://img.shields.io/badge/Weka-J48-006400?style=flat)]()
[![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=flat&logo=eclipseide&logoColor=white)]()

[Descripción](#-sobre-el-proyecto) · [Cómo funciona](#-cómo-funciona) · [Cómo ejecutarlo](#-cómo-ejecutarlo) · [Datos](#-los-datos) · [Estructura](#-estructura-del-repositorio) · [Stack](#-stack-tecnológico)

</div>

---

## 📖 Sobre el proyecto

Un agente, **Guesser**, adivina el personaje famoso en el que está pensando el jugador. Para ello hace preguntas de sí o no, y cada respuesta lo acerca al personaje. Detrás hay dos ideas: **agentes que se comunican por mensajes ACL** (JADE) y un **clasificador de Weka (J48)** entrenado con un cuestionario sobre famosos, cuyo árbol de decisión se recorre para decidir qué preguntar.

> 📌 **Proyecto académico** realizado en grupo para la asignatura de *Programación Avanzada* (3.er curso, Universidad de Salamanca).

---

## 🧠 Cómo funciona

```
Cliente                                   Guesser (AgentQuienEsQuien)
   │  REQUEST  "Hola Guesser"                  │
   │ ────────────────────────────────────────▶ │  entrena J48 con famosos.csv
   │  INFORM   "PREGUNTA: ¿...?"               │
   │ ◀──────────────────────────────────────── │
   │  INFORM   "s" / "n"                       │  baja por la rama del árbol
   │ ────────────────────────────────────────▶ │
   │            ... (se repite)                │
   │  INFORM   "Es <personaje>"                │  llega a una hoja
   │ ◀──────────────────────────────────────── │
```

- **`AgentQuienEsQuien`** se registra en las páginas amarillas (*Directory Facilitator*) con el servicio `quienesquien` y lanza **cinco comportamientos en hilos paralelos**, para atender a varios clientes a la vez.
- **`BehaviourQuienEsQuien`** carga `famosos.csv`, construye un árbol **J48** de Weka y, mediante la clase `Pregunta`, **navega el árbol** nodo a nodo: cada nodo es una pregunta y cada rama una respuesta, hasta llegar a una hoja, que es el personaje.
- **`AgentCliente`** inicia la conversación, muestra cada pregunta por consola y envía la respuesta (`s`/`n`). La conversación termina cuando el mensaje empieza por `Es `.

---

## 🚀 Cómo ejecutarlo

**Requisitos:** JDK 8 o superior y Eclipse. Las librerías no se incluyen en el repositorio; descárgalas y colócalas en `lib/`:

| Librería | Uso |
| :--- | :--- |
| `jade.jar` | Plataforma de agentes ([jade.tilab.com](https://jade.tilab.com/)) |
| `weka.jar` | Clasificador J48 ([cs.waikato.ac.nz/ml/weka](https://ml.cms.waikato.ac.nz/weka/)) |
| `commons-codec-1.3.jar` | Dependencia de JADE |

1. Importa el proyecto en Eclipse (el nombre del proyecto esperado por las configuraciones es `weka2`).
2. Ejecuta primero **`launchAgents/Guesser.launch`**, que arranca el contenedor principal con la interfaz de JADE.
3. Ejecuta después **un cliente** (por ejemplo `Unai.launch`) y responde a las preguntas por consola con `s` o `n`.

Equivalente por línea de comandos (Windows; en Linux usa `:` en lugar de `;`):

```bash
# Servidor
java -cp "bin;lib/*" jade.Boot -gui Guesser:agents.AgentQuienEsQuien

# Cliente (en otra terminal)
java -cp "bin;lib/*" jade.Boot -main false -host 127.0.0.1 Unai:agents.AgentCliente
```

---

## 🗂️ Los datos

- **`famosos.csv`**: un cuestionario de sí/no sobre cada personaje (si es mujer, a qué se dedica, si nació en España, si sigue vivo...). Es la única fuente del árbol que usa el juego.
- **`famosos.xlsx`**: la misma tabla en hoja de cálculo.
- **`mushroom.csv` y `mushroom2.csv`**: el conjunto de datos público *Mushroom* de UCI, incluido como material de prueba; el juego no lo usa.

---

## 📂 Estructura del repositorio

```
Akinator-JADE/
├── src/
│   ├── agents/
│   │   ├── AgentQuienEsQuien.java   # Servidor: registro en el DF y comportamientos en paralelo
│   │   └── AgentCliente.java        # Cliente interactivo
│   ├── behaviours/
│   │   ├── BehaviourQuienEsQuien.java
│   │   └── BehaviourCliente.java
│   └── utilities/
│       ├── Pregunta.java            # Recorrido del árbol J48
│       └── Utils.java               # Envío, lectura y búsqueda de agentes
├── launchAgents/                    # Configuraciones de ejecución de Eclipse
├── famosos.csv · famosos.xlsx
└── mushroom.csv · mushroom2.csv
```

---

## 🖥️ Stack tecnológico

| Categoría | Herramientas |
| :--- | :--- |
| **Lenguaje** | Java |
| **Agentes** | JADE · mensajería ACL · Directory Facilitator · comportamientos cíclicos y paralelos |
| **Aprendizaje automático** | Weka · árbol de decisión J48 |
| **Entorno** | Eclipse |

---

## 🎓 Qué aprendí

- **Diseñar un sistema multiagente**: servicios, páginas amarillas y conversaciones identificadas.
- **Atender a varios clientes a la vez** con comportamientos en hilos.
- **Usar un árbol de decisión como motor de preguntas**, no solo como clasificador.

---

## 📝 Notas

La hoja original de fichas con las fotos de los personajes no se incluye por tratarse de imágenes de terceros.

---

## 👤 Autoría

**Unai García** — Grado en Ingeniería Informática (Universidad de Salamanca)

- 🐙 GitHub: [@unaigb](https://github.com/unaigb)
- 🌐 Portfolio: [unaigb.dev](https://unaigb.dev)

---

<div align="center">

**⭐ Si este repositorio te resulta útil, considera darle una estrella.**

</div>
