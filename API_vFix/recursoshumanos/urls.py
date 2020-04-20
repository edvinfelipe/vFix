from django.urls import path
from .views import RHAPIView, RHDetalle, Login

urlpatterns = [
    path('api/recursoshumanos/', RHAPIView.as_view()),
    path('api/recursoshumanos/<int:pk>/', RHDetalle.as_view()),
    path('api/login/', Login.as_view())
]
