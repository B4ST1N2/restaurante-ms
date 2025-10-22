resource "aws_security_group" "ssh" {
  name        = "${var.project_name}-sg"
  description = "Permite SSH y puerto de la app"
  vpc_id      = aws_vpc.main.id

  # SSH (22) solo tu IP
  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.my_ip_cidr]
  }

  # App HTTP (8080) - abierto a todos para demo
  ingress {
    description = "HTTP app (demo)"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = { Name = "${var.project_name}-sg" }
}
