import api from "@/services/api";
import type IAuditLog from "@/interfaces/IAuditLog";

class AdminAuditLogService {

    async getAllAuditLogs() {
        const result = await api.get('admin/get-all-audit-logs', {});
        return result;
    }

    async getAuditLogsByAction(action: String) {
        return await api.get('/admin/get-audit-logs-by-action/' + action, {});
    }
}

export default new AdminAuditLogService();