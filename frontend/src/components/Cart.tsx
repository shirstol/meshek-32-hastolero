import type { CartItem } from '../types/cart'

type CartProps = {
  items: CartItem[]
  total: number
  onIncrease: (productId: string) => void
  onDecrease: (productId: string) => void
  onContinue: () => void
}

export function Cart({ items, total, onIncrease, onDecrease, onContinue }: CartProps) {
  return (
    <aside className="cart-panel" aria-labelledby="cart-title">
      <div className="cart-panel__heading">
        <div><p>ההזמנה שלך</p><h2 id="cart-title">הסל</h2></div>
        <span>{items.reduce((sum, item) => sum + item.quantity, 0)} פריטים</span>
      </div>
      {items.length === 0 ? <p className="cart-panel__empty">הוסיפו מוצרים מהקטלוג כדי להתחיל.</p> : <>
        <ul className="cart-panel__items">
          {items.map(({ product, quantity }) => <li key={product.id}>
            <div><strong>{product.name}</strong><span>₪{product.price} ליחידה</span></div>
            <div className="quantity-control" aria-label={`כמות ${product.name}`}>
              <button type="button" onClick={() => onDecrease(product.id)} aria-label={`הסרת יחידה של ${product.name}`}>−</button>
              <span>{quantity}</span>
              <button type="button" onClick={() => onIncrease(product.id)} aria-label={`הוספת יחידה של ${product.name}`} disabled={product.maxQuantity !== null && quantity >= product.maxQuantity}>+</button>
            </div>
          </li>)}
        </ul>
        <div className="cart-panel__total"><span>סך הכול</span><strong>₪{total}</strong></div>
        <button type="button" className="button button--primary button--wide" onClick={onContinue}>להמשך לפרטי איסוף</button>
      </>}
    </aside>
  )
}
