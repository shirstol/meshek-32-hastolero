import type { DistributionPoint } from './distributionPoint'
import type { Product } from './product'

export type PaymentMethod = 'NONE' | 'BIT' | 'PAYBOX'
export type OrderStatus = 'RECEIVED' | 'PREPARING' | 'READY_FOR_PICKUP' | 'DELIVERED' | 'CANCELLED'

export type Customer = { id: number; fullName: string; phone: string }
export type OrderItem = { productId: string; productName: string; unitPrice: number; quantity: number }
export type Order = {
  id: number
  orderNumber: string
  customer: Customer
  distributionPoint: DistributionPoint
  items: OrderItem[]
  total: number
  status: OrderStatus
  packed: boolean
  adminNote: string | null
  paymentMethod: PaymentMethod
  paymentStatus: string
  createdAt: string
}
export type Dashboard = {
  totalOrders: number
  packedOrders: number
  openOrders: number
  totalRevenue: number
  byPickupPoint: { distributionPointId: string; distributionPointName: string; orders: number; revenue: number }[]
}
export type { Product }
