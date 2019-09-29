from django.urls import path
from categoria import views

urlpatterns = [
    path('api/categorias/', views.CategoriaAPIView.as_view()),
    path('api/categorias/<int:pk>/', views.CategoriaDetalle.as_view()),
]
