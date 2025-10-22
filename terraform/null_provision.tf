resource "null_resource" "db_scripts" {
  depends_on = [aws_instance.db]

  connection {
    type        = "ssh"
    host        = aws_instance.db.public_ip
    user        = "ubuntu"
    private_key = tls_private_key.ssh.private_key_pem
  }

  # 1) Espera a que cloud-init termine (user_data ya corrió)
  provisioner "remote-exec" {
    inline = [
      # Espera a que el user_data (cloud-init) termine; si falla, espera un poco
      "cloud-init status --wait || sleep 30"
    ]
  }

  # 2) Sube los archivos a /home/ubuntu (donde sí tiene permisos)
  provisioner "file" {
    source      = "${path.module}/sql"
    destination = "/home/ubuntu/sql"
  }

  provisioner "file" {
    source      = "${path.module}/scripts/run_db_scripts.sh"
    destination = "/home/ubuntu/run_db_scripts.sh"
  }

  # 3) Mueve a /opt/sql con sudo y ejecuta los scripts
  provisioner "remote-exec" {
    inline = [
      "chmod +x /home/ubuntu/run_db_scripts.sh",
      "sudo mv /home/ubuntu/sql /opt/sql",
      "sudo chown -R root:root /opt/sql",
      "export MYSQL_ROOT_PASSWORD='${var.db_password}'",
      "export MYSQL_DB='${var.mysql_db_name}'",
      "bash -lc '/home/ubuntu/run_db_scripts.sh'"
    ]
  }
}
