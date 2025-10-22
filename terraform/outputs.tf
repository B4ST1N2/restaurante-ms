output "ec2_public_ip" { description = "IP pública de la VM"; value = aws_instance.db.public_ip }
output "ssh_command"   { description = "SSH"; value = "ssh -i ./db_key.pem ubuntu@${aws_instance.db.public_ip}" }
output "s3_bucket"     { description = "Bucket S3"; value = aws_s3_bucket.artifacts.bucket }
