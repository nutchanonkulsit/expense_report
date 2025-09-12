export interface User {
    id?: number;
    name?: string;
    email?: string;
    role?: 'admin' | 'user' | 'guest';
    isActive?: boolean;
    createdAt?: Date;
    updatedAt?: Date;
    password?: string;
    confirmPassword?: string;
}