export type Product = {
  id: string
  name: string
  description: string
  category?: string | null
  imageUrl?: string | null
  price: number
  available: boolean
  maxQuantity: number | null
}
