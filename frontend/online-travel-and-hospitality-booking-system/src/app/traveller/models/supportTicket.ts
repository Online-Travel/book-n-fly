export interface SupportTicket {
  ticketId: number;
  issue: string;
  status?: string;
  assignedAgentId?: number | null;
  userId?: number;
  createdAt?: string;
}
export type CreateSupportTicket = Omit<SupportTicket, 'ticketId'>;
