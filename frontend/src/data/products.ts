import type { Product } from '../types/product'

export const products: Product[] = [
  {
    id: 'butterhead-lettuce',
    name: 'חסה מסולסלת',
    description: 'חסה טרייה שנקטפה במשק.',
    price: 10,
    available: true,
    maxQuantity: 2,
  },
  {
    id: 'romaine-lettuce',
    name: 'חסה רומית',
    description: 'עלים פריכים המתאימים לסלט ולכריך.',
    price: 12,
    available: true,
    maxQuantity: null,
  },
  {
    id: 'mixed-greens',
    name: 'מארז עלים ירוקים',
    description: 'מבחר עלים טריים מהחממה.',
    price: 18,
    available: false,
    maxQuantity: null,
  },
]
