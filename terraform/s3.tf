resource "random_id" "suffix" { byte_length = 2 }

resource "aws_s3_bucket" "artifacts" {
  bucket        = "${var.project_name}-artifacts-${random_id.suffix.hex}"
  force_destroy = true
  tags          = { Name = "${var.project_name}-artifacts" }
}

resource "aws_s3_bucket_public_access_block" "pab" {
  bucket                  = aws_s3_bucket.artifacts.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}
