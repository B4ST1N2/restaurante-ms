resource "null_resource" "db_scripts" {
  depends_on = [aws_instance.db]

  connection {
    type        = "ssh"
    host        = aws_instance.db.public_ip
    user        = "ubuntu"
    private_key = tls_private_key.ssh.private_key_pem
  }

  provisioner "file" {
    source      = "${path.module}/sql"
    destination = "/opt"
  }

  provisioner "file" {
    source      = "${path.module}/scripts/run_db_scripts.sh"
    destination = "/home/ubuntu/run_db_scripts.sh"
  }

  provisioner "remote-exec" {
    inline = [
      "chmod +x /home/ubuntu/run_db_scripts.sh",
      "export MYSQL_ROOT_PASSWORD='${var.db_password}'",
      "export MYSQL_DB='${var.mysql_db_name}'",
      "bash -lc /home/ubuntu/run_db_scripts.sh"
    ]
  }
}
