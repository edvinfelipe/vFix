from django.urls import path
from .views import FacturaAPIView, FacturaDetalle
urlpatterns = [
    path('api/facturas/', FacturaAPIView.as_view()),
    path('api/facturas/<int:pk>/', FacturaDetalle.as_view())
]
