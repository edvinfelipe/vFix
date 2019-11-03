from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import FacturaSerializers, FacturaMovilSerializers
from .models import Factura
from django.db.models import Sum
import datetime

# Create your views here.
class FacturaAPIView(APIView):

    def post(self, request):

        serializer = FacturaSerializers(data= request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors)
     
    def get(self, request):
        try:
            facturas = Factura.objects.filter(eliminado=False)
        except Factura.DoesNotExist:
            return Response({'Error': 'Hubo error en la obtención de las facturas'})
        
        serializer = FacturaSerializers(facturas, many=True)
        return Response(serializer.data)


class FacturaDetalle(APIView):
    def get(self, request, pk):
        try:
            factura = Factura.objects.get(pk=pk, eliminado=False)
        except:
            return Response({'Error': 'Hubo error en la obtención de la factura'})
        serializer = FacturaSerializers(factura, many=False)
        return Response(serializer.data)
    
    def delete(self, request, pk):
        try:
            factura = Factura.objects.get(pk=pk,eliminado=False)
        except:
            return Response({'Error': 'La factura no existe'})
        factura.deleted()
        return Response({'mensaje':'La factura se canceló con éxito'})

class FacturaMovil(APIView):
    def get(self, request, year):

        try:
            json = {}
            for month in range(12):
                start_date = datetime.date(year, month+1,1)
                

                if (month+1) == 2:
                    end_date = datetime.date(year, month+1, 28)
                elif (month+1) == 4:
                    end_date = datetime.date(year, month+1, 30)
                elif (month+1) == 6:
                    end_date = datetime.date(year, month+1, 30)
                elif (month+1) == 9:
                    end_date = datetime.date(year, month+1, 30)
                elif (month+1) == 11:
                    end_date = datetime.date(year, month+1, 30)
                else:
                    end_date = datetime.date(year, month+1, 31)

                suma = Factura.objects.filter(fecha__range=(start_date, end_date)).aggregate(Sum('total'))
                json[month+1] = suma.get('total__sum')
                          
            return Response(json)
        except:
            return Response({'Error': 'Hubo error en el filtrado'})