from django.db import models

# Create your models here.
class humanInfo(models.Model):
    name = models.CharField(max_length=10)
    phone_number = models.CharField(max_length=3)
    address = models.TextField()
    
    created = models.DateTimeField(auto_now_add=True)
    
    class Meta: ordering = ['created']  # 정렬 순서