from django.urls import path
from cliente import views
urlpatterns = [
    path('api/clientes/', views.ClienteAPIView.as_view()),
    path('api/clientes/filtrar/', views.ClienteFilter.as_view()),
    path('api/clientes/<int:pk>/', views.ClienteDetalle.as_view()),
]
