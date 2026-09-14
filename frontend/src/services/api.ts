import type { Dashboard, Order, OrderStatus, PaymentMethod, StoreCatalog } from '../types/api'
import type { DistributionPoint } from '../types/distributionPoint'
import type { Product } from '../types/product'

const API_URL = 'http://127.0.0.1:8080/api'

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const response = await fetch(`${API_URL}${path}`, { headers: { 'Content-Type': 'application/json', ...options?.headers }, ...options })
  if (!response.ok) {
    const message = await response.text()
    throw new Error(message || 'פעולת השרת נכשלה')
  }
  return response.json() as Promise<T>
}

export const api = {
  products: () => request<Product[]>('/products'),
  store: (slug: string) => request<StoreCatalog>(`/stores/${slug}`),
  distributionPoints: () => request<DistributionPoint[]>('/distribution-points'),
  identifyCustomer: (fullName: string, phone: string) => request('/customers/identify', { method: 'POST', body: JSON.stringify({ fullName, phone }) }),
  createOrder: (input: { fullName: string; phone: string; distributionPointId: string; items: { productId: string; quantity: number }[]; paymentMethod: PaymentMethod }) => request<Order>('/orders', { method: 'POST', body: JSON.stringify({ customer: { fullName: input.fullName, phone: input.phone }, distributionPointId: input.distributionPointId, items: input.items, paymentMethod: input.paymentMethod }) }),
  adminOrders: (credentials: string, filters: Record<string, string>) => request<Order[]>(`/admin/orders?${new URLSearchParams(Object.entries(filters).filter(([, value]) => value)).toString()}`, { headers: { Authorization: `Basic ${credentials}` } }),
  dashboard: (credentials: string, pickupPointId = '') => request<Dashboard>(`/admin/dashboard?${new URLSearchParams(pickupPointId ? { pickupPointId } : {}).toString()}`, { headers: { Authorization: `Basic ${credentials}` } }),
  updateOrder: (credentials: string, id: number, status: OrderStatus, packed: boolean, adminNote: string) => request<Order>(`/admin/orders/${id}`, { method: 'PATCH', headers: { Authorization: `Basic ${credentials}` }, body: JSON.stringify({ status, packed, adminNote }) }),
  addStoreProduct: (credentials: string, slug: string, input: { name: string; description: string; category: string; imageUrl: string; price: number; available: boolean; maxQuantity: number | null }) => request<Product>(`/admin/stores/${slug}/products`, { method: 'POST', headers: { Authorization: `Basic ${credentials}` }, body: JSON.stringify(input) }),
}
