 🚗 Sistema de Entrega de Licencias

Proyecto académico de Programación Orientada a Objetos (POO) desarrollado en Java Swing, con conexión a base de datos remota en la nube.  
El sistema automatiza el proceso de matriculación vehicular y emisión de licencias, incluyendo login seguro con roles, gestión de trámites y generación de reportes.

✨ Características principales
- Login con roles: ADMIN y ANALISTA, con control de intentos y usuarios activos/inactivos.
- Gestión de solicitantes: registro, validación de requisitos y exámenes.
- Flujo de trámites: estados claros (pendiente → en_examenes → aprobado/reprobado → licencia_emitida).
- Generación de licencias: vista previa, exportación a PDF y control de duplicados.
- Gestión de usuarios (ADMIN): crear, modificar, activar/desactivar cuentas.
- Reportes y estadísticas: filtros por fecha, estado, tipo de licencia y exportación a CSV/Excel.
- Auditoría mínima: `created_by`, `created_at` en tablas críticas.
- POO aplicada: encapsulamiento, herencia, polimorfismo y abstracción en toda la arquitectura.

🛠️ Tecnologías usadas
- Lenguaje: Java (Swing, MVC).
- Base de datos: Postgres en Supabase (alternativas: MySQL en Railway/Render).
- Drivers: JDBC.
- Seguridad: Hash de contraseñas con BCrypt.
- Empaquetado: `.jar` y opcional `.exe` (Launch4j / jpackage).
- 
 📂 Estructura del proyecto
├── model         Clases modelo (POJOs)
├── dao           Interfaces e implementaciones de acceso a BD
├── service       Lógica de negocio y validaciones
├── view            Formularios y ventanas en Swing
├── docs          Manuales de usuario y técnico
└── build         Artefactos (.jar/.exe)

USUARIO ADMIN:
Usuario: admin1
Contraseña: 25122004

USUARIO ANALISTA
Usuario:jairo
Contraseña: $jairo1234
