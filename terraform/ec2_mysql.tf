data "aws_ami" "ubuntu" {
  most_recent = true
  owners      = ["099720109477"]
  filter { name = "name" values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"] }
  filter { name = "virtualization-type" values = ["hvm"] }
}

resource "aws_instance" "db" {
  ami                    = data.aws_ami.ubuntu.id
  instance_type          = var.instance_type
  subnet_id              = aws_subnet.public.id
  vpc_security_group_ids = [aws_security_group.ssh.id]
  key_name               = aws_key_pair.ssh.key_name

  user_data = <<-EOF
              #!/bin/bash
              set -euxo pipefail

              apt-get update -y
              DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server openjdk-17-jre

              # Configura MySQL
              mysql --protocol=socket -u root <<SQL
              ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '${var.db_password}';
              FLUSH PRIVILEGES;
              CREATE DATABASE IF NOT EXISTS ${var.mysql_db_name} CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
              CREATE USER IF NOT EXISTS '${var.mysql_app_user}'@'localhost' IDENTIFIED BY '${var.db_password}';
              GRANT ALL PRIVILEGES ON ${var.mysql_db_name}.* TO '${var.mysql_app_user}'@'localhost';
              FLUSH PRIVILEGES;
              SQL

              mkdir -p /opt/sql
              chown -R ubuntu:ubuntu /opt/sql
              EOF

  tags = { Name = "${var.project_name}-mysql-vm" }
}
