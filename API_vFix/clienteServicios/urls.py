from django.urls import path
from clienteServicios import views

urlpatterns = [
    path('api/clienteservicios/', views.ClienteServicioGETPOST.as_view()),
    path('api/clienteservicios/<int:pk>/', views.ClienteServicioGETPUTDELETE.as_view()),
]