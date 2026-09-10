import type { Product } from '../types/product'

type ProductCardProps = {
  product: Product
  quantityInCart: number
  onAddToCart: (product: Product) => void
}

export function ProductCard({ product, quantityInCart, onAddToCart }: ProductCardProps) {
  const reachedLimit = product.maxQuantity !== null && quantityInCart >= product.maxQuantity

  return (
    <article className="product-card">
      <div className="product-card__image" aria-hidden="true">🥬</div>
      <div className="product-card__body">
        <p className={product.available ? 'availability availability--available' : 'availability'}>
          {product.available ? 'זמין השבוע' : 'אזל מהמלאי'}
        </p>
        <h3>{product.name}</h3>
        <p className="product-card__description">{product.description}</p>
      </div>
      <div className="product-card__footer">
        <div>
          <strong>₪{product.price}</strong>
          {product.maxQuantity !== null && <span>עד {product.maxQuantity} יח׳ ללקוח</span>}
        </div>
        <button type="button" onClick={() => onAddToCart(product)} disabled={!product.available || reachedLimit}>
          {!product.available ? 'לא זמין' : reachedLimit ? 'הגעת למגבלה' : quantityInCart ? `הוספה נוספת · ${quantityInCart} בסל` : 'הוספה לסל'}
        </button>
      </div>
    </article>
  )
}
