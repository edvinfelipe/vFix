from django.urls import path
from .views import ClienteAPIView, ClienteDetalle
urlpatterns = [
    path('api/clientes/', ClienteAPIView.as_view()),
    path('api/clientes/<slug:codigo>/', ClienteDetalle.as_view()),
]
