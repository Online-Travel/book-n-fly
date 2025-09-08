export interface Review {
    reviewId: number;
    userId: number;
    hotelId: number;
    rating: number;
    comment: string;
    timestamp: string; // ISO date string
}