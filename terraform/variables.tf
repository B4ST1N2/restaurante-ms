variable "project_name"  { type = string, default = "restaurante-ms" }
variable "region"        { type = string, default = "us-east-1" }

variable "db_password"   { description = "Password para root y app (demo)"; type = string; sensitive = true }
variable "my_ip_cidr"    { description = "Tu IP pública X.Y.Z.W/32"; type = string; default = "0.0.0.0/0" }

variable "instance_type" { type = string, default = "t3.micro" }
variable "mysql_app_user"{ type = string, default = "appuser" }
variable "mysql_db_name" { type = string, default = "restaurante_db" }
