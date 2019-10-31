from django.urls import path
from .views import FacturaAPIView, FacturaDetalle, FacturaMovil
urlpatterns = [
    path('api/facturas/', FacturaAPIView.as_view()),
    path('api/facturas/<int:pk>/', FacturaDetalle.as_view()),
    path('api/facturas/movil/<int:year>/', FacturaMovil.as_view())
]
