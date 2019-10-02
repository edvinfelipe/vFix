from rest_framework import serializers
from .models import RH

class RHSerializers(serializers.ModelSerializer):
    class Meta:
       model = RH
       fields = ['codigo','nombre','rol','imagen']
