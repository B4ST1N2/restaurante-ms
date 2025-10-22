#!/usr/bin/env bash
set -euo pipefail

echo "[INFO] Ejecutando scripts MySQL por SSH"
MYSQL_ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD:?sin password}"
MYSQL_DB="${MYSQL_DB:-restaurante_db}"

mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "CREATE DATABASE IF NOT EXISTS ${MYSQL_DB} CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

echo "[INFO] 01_create.sql"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" "${MYSQL_DB}" < /opt/sql/01_create.sql

echo "[INFO] 02_seed_and_select.sql"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" "${MYSQL_DB}" < /opt/sql/02_seed_and_select.sql

echo "[INFO] 03_drop.sql (elimina la BD completa)"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" < /opt/sql/03_drop.sql

echo "[OK] Scripts finalizados"
