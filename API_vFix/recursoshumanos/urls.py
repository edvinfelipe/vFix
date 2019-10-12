from django.urls import path
from .views import RHAPIView, RHDetalle

urlpatterns = [
    path('api/recursoshumanos/', RHAPIView.as_view()),
    path('api/recursoshumanos/<slug:codigo>/', RHDetalle.as_view())
]
