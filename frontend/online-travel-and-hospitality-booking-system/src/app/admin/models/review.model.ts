export interface Review {
  reviewId?: number;     
  userId: number;
  hotelId: number;
  rating: number;
  comment: string;
  timestamp?: string;   
}

export interface SupportTicket {
  ticketId?: number;       
  userId: number;
  issue: string;
  status: string;          
  assignedAgentId?: number; 
  createdAt?: string;       
}
